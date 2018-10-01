package org.kingson.Ims.workflow.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.Ims.workflow.dao.BusinessDataRepository;
import org.kingson.Ims.workflow.domain.BusinessData;
import org.kingson.Ims.workflow.service.WorkflowService;
import org.kingson.Ims.workflow.vo.ProcessForm;
import org.kingson.Ims.workflow.vo.ProcessImage;
import org.kingson.Ims.workflow.vo.TaskItem;
import org.kingson.commrs.DatePropertyEditor;
import org.kingson.commrs.pager.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;

@Service
public class WorkflowServiceImpl implements WorkflowService, ApplicationContextAware {

	// 日志记录器
	private static final Logger LOG = LoggerFactory.getLogger(WorkflowServiceImpl.class);
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService formService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	// 单词错误了！
	@Autowired
	private IndentityService indentityService;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void deploy(String name, InputStream in) {
		// 流程定义管理使用RepositoryService，操作ACT_RE_开头表
		// 1.把输入流，转换为ZipInputStream，因为它是ZIP格式压缩的
		try (ZipInputStream zip = new ZipInputStream(in)) {
			// 2.创建流程定义部署的构建器，设置相应的参数
			DeploymentBuilder builder = this.repositoryService.createDeployment();

			builder.name(name);// 部署的文件名
			builder.addZipInputStream(zip);// 设置要部署的内容

			// 3.执行部署，在ACT_RE_PROCDEF表中会增加对应的流程定义
			builder.deploy();
		} catch (IOException e) {
			throw new RuntimeException("部署流程定义异常，因为:" + e.getMessage(), e);
		}
	}

	@Override
	public List<ProcessDefinition> findProcessDefinitions(PageModel pageModel) {

		// 1.创建查询对象
		ProcessDefinitionQuery query = this.repositoryService.createProcessDefinitionQuery();
		// 查询最后一个版本的流程定义，当流程定义的KEY相同的时候，只会增加版本号，不会修改原有数据
		// 不会update旧的的记录！
		query.latestVersion();

		// 其他的所有查询条件，都是封装好了的！
		// query.processDefinitionNameLike("%请假%");

		// 按照流程定义的KEY排序
		query.orderByProcessDefinitionKey().asc();

		// 2.统计总记录数
		long count = query.count();
		pageModel.setRecordCount((int) count);
		// 3.查询当前页的数据
		// list()方法，不分页
		// listPage()方法，分页
		List<ProcessDefinition> list = query.listPage(pageModel.getFirstLimitParam(), pageModel.getPageSize());

		return list;
	}

	@Override
	public void activeProcessDefinition(String id) {
		this.repositoryService.activateProcessDefinitionById(id);
	}

	@Override
	public void suspendProcessDefinition(String id) {
		this.repositoryService.suspendProcessDefinitionById(id);
	}

	@Override
	public ProcessDefinition getProcessDefinitionById(String id) {
		return this.repositoryService.getProcessDefinition(id);
	}

	@Override
	public ProcessImage getProcessDefinitionImageById(String id) {
		ProcessDefinition def = this.getProcessDefinitionById(id);
		// 图片名
		String name = def.getDiagramResourceName();
		// 图片内容
		try (InputStream in = this.repositoryService.getProcessDiagram(id);) {
			ProcessImage image = new ProcessImage(name, in);
			return image;
		} catch (IOException e) {
			throw new RuntimeException("无法处理图片内容，因为:" + e.getMessage(), e);
		}
	}
    //!!!!!!!!!!!!!!!!!!核心代码
	@Override
	public ProcessForm getStartForm(String key) {
		ProcessForm pf = new ProcessForm();

		ProcessDefinition definition = this.repositoryService//
				.createProcessDefinitionQuery()// 创建流程定义查询对象
				.processDefinitionKey(key)// 根据流程定义的KEY查询
				.latestVersion()// 查询最后一个版本
				.singleResult();// 返回单个结果
		pf.setDefinition(definition);

		// 使用FormService来查询表单相关的信息
		StartFormData formData = this.formService.getStartFormData(definition.getId());
		pf.setFormData(formData);
		String formkey = this.formService.getStartFormKey(definition.getId());
		pf.setFormKey(formkey);
		try {
			// 虽然获取出来的是一个Object，其实它是String，直接输出即可显示内容
			Object formContent = this.formService.getRenderedStartForm(definition.getId());
			pf.setFormContent(formContent);
		} catch (Exception e) {
			// 出的异常被称之为“表单不存在”，ActivitiObjectNotFoundException
			// 在formKey没有对应表单文件的时候，就可能会出现此问题
			// 出现这种异常，不需要任何处理，因为没有表单，可能就希望把formKey作为JSP的路径来使用
		}

		return pf;
	}

	@Override
	@Transactional(noRollbackFor = { ActivitiObjectNotFoundException.class })
	public void startProcessInstance(String id, Map<String, String[]> params) {
		// 1.整理请求参数，如果请求参数的值只有一个，那么直接使用String，方便流程里面使用
		Map<String, Object> variables = new HashMap<>();
		params.forEach((key, values) -> {
			if (values.length == 1) {
				variables.put(key, values[0]);
			} else {
				variables.put(key, values);
			}
		});
		// 2.查询流程定义
		ProcessDefinition definition = this.getProcessDefinitionById(id);
		// 3.保存业务数据，保存业务数据以后需要返回业务数据的主键值，用于关联业务数据和流程实例
		String businessKey = saveBusinessData(definition, params);

		// 4.启动流程实例，需要使用RuntimeService
		ProcessInstance instance = this.runtimeService.startProcessInstanceById(id, businessKey, variables);

		// 5.记录流程跟踪信息
	}

	@SuppressWarnings("unchecked")
	private String saveBusinessData(ProcessDefinition definition, Map<String, String[]> params) {
		// 1.根据FormService查询所有开始事件的表单属性，分别得到业务数据的类名和DAO的类名
		StartFormData formData = this.formService.getStartFormData(definition.getId());
		List<FormProperty> props = formData.getFormProperties();
		String businessDataClassName = null;
		for (FormProperty p : props) {
			if (p.getId().equals("businessDataClassName")) {
				businessDataClassName = p.getValue();
			}
		}
		if (businessDataClassName == null) {
			// 没有配置业务数据对象的类名
			LOG.debug("{}流程没有配置业务数据对象的实体类名，无法保存业务数据", definition.getName());
			return null;
		}
		String businessDataDaoClassName;
		try {
			businessDataDaoClassName = props.stream()// 转换为流式API
					// filter等同于前面的if判断
					.filter((p) -> p.getId().equals("businessDataDaoClassName"))
					// 找到第一个结果，返回的是Optional类型的对象，通过get方法获取返回的值
					.findFirst().get()
					// 返回的值就是FormProperty，所以直接调用getValue()方法
					.getValue();
		} catch (NullPointerException e) {
			LOG.debug("{}流程没有配置业务数据对象的DAO实现类名，无法保存业务数据", definition.getName());
			return null;
		}

		// 2.创建业务数据实体类的对象，把请求参数填充到对象里面
		Class<? extends BusinessData> businessDataClass;
		try {
			businessDataClass = (Class<? extends BusinessData>) Class.forName(businessDataClassName);
		} catch (ClassNotFoundException e) {
			LOG.error("{}流程配置的业务数据实体类无法加载，无法保存业务数据", definition.getName());
			LOG.debug(e.getMessage(), e);
			return null;
		}
		BusinessData data;
		try {
			data = businessDataClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.error("{}流程配置的业务数据实体类无法创建实例，无法保存业务数据", definition.getName());
			LOG.debug(e.getMessage(), e);
			return null;
		}
		// 把请求参数，填充到data对象里面
		// WebDataBinder是在Spring MVC里面自定义数据类型转换器的时候，用于注册转换器使用的
		WebDataBinder binder = new WebDataBinder(data);
		// 注入自定义的日期时间转换器
		binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy-MM-dd HH:mm"));

		// 执行转换，data里面就已经有足够的数据了！
		PropertyValues pvs = new MutablePropertyValues(params);
		binder.bind(pvs);

		// 由于业务数据，未使用主键生成器，所以自己生成一个
		String id = null;
		if (StringUtils.isNoneBlank(data.getId())) {
			// 有id，修改数据
			id = data.getId();
		} else {
			data.setId(UUID.randomUUID().toString());
			data.setSubmitTime(new Date());

			// 要在拦截器里面，把当前User设置到一个ThreadLocal类型的对象里面
			User user = new User();
			user.setUserId(Authentication.getAuthenticatedUserId());
			data.setUser(user);
		}

		// 3.找到DAO对象，调用save方法
		// 需要获取Spring的容器，才能获取DAO对象，所以当前类需要实现ApplicationContextAware接口
		Class<? extends BusinessDataRepository<?>> businessDataDaoClass;
		try {
			businessDataDaoClass = (Class<? extends BusinessDataRepository<?>>) Class.forName(businessDataDaoClassName);
		} catch (ClassNotFoundException e) {
			LOG.error("{}流程配置的业务数据DAO类无法加载，无法保存业务数据", definition.getName());
			LOG.debug(e.getMessage(), e);
			return null;
		}

		BusinessDataRepository<? extends BusinessData> dao = this.applicationContext.getBean(businessDataDaoClass);

		// 如果不希望先删除，再插入。那么就需要复杂对象的DAO重写save方法，把修改数据的逻辑写在save里面。
		if (id != null) {
			// 有id的时候，把一些不变的数据，从数据库取出来
			BusinessData old = dao.findById(id);
			data.setSubmitTime(old.getSubmitTime());
			data.setUser(old.getUser());

			// 删除旧的数据，然后再重新插入，这样可以非常简单解决关联关系的问题，特别一对多、多对多。
			// 多的一端如果使用了主键生成器，那么页面就不要再把id传过来，直接生成新的主键。
			dao.delete(old);
		}

		dao.save(data);

		// 保存以后，返回主键的值
		return data.getId();
	}

	@Override
	public List<TaskItem> findTasks(PageModel pageModel) {
		// 获取到当前用户的ID
		String userId = Authentication.getAuthenticatedUserId();
		// 调用TaskService的方法查询数据
		TaskQuery query = this.taskService.createTaskQuery();

		// 根据任务的处理人查询
		query.taskAssignee(userId);
		// 根据任务的创建时间排序
		query.orderByTaskCreateTime().desc();

		// 查询总记录数
		long count = query.count();
		pageModel.setRecordCount((int) count);

		// 查询数据，只包含了任务名称、任务的创建时间
		List<Task> taskList = query.listPage(pageModel.getFirstLimitParam(), pageModel.getPageSize());

		// 查询关联数据，只有单表操作，所以关联数据需要自己分开查
		List<TaskItem> list = new LinkedList<>();
		taskList.forEach(task -> {
			TaskItem item = new TaskItem();
			// add在前、在后没有区别，因为item指向的对象没有发生改变
			list.add(item);
			fillTaskItem(item, task);
		});

		return list;
	}

	private void fillTaskItem(TaskItem item, Task task) {
		item.setTask(task);

		// 查询流程实例，使用历史服务查询
		HistoricProcessInstance instance = this.historyService//
				.createHistoricProcessInstanceQuery()//
				.processInstanceId(task.getProcessInstanceId())//
				.singleResult();
		item.setInstance(instance);

		// 查询流程定义
		ProcessDefinition definition = this.getProcessDefinitionById(task.getProcessDefinitionId());
		item.setDefinition(definition);

		// 查询创始人
		// 通过拦截器授权以后启动的流程实例，才会有startUserId
		if (instance.getStartUserId() != null) {
			User initiator = this.indentityService.getUserById(instance.getStartUserId());
			item.setInitiator(initiator);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TaskItem findTaskById(String taskId) {
		TaskItem item = new TaskItem();
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		this.fillTaskItem(item, task);

		// 完成任务的表单，其实跟启动流程实例的差不多
		// 查询任务formKey
		String formKey = this.formService.getTaskFormKey(item.getDefinition().getId(), task.getTaskDefinitionKey());
		item.setFormKey(formKey);
		// 查询表单内容
		try {
			Object formContent = this.formService.getRenderedTaskForm(taskId);
			item.setFormContent(formContent);
		} catch (Exception e) {
		}
		// 查询表单属性
		TaskFormData formData = this.formService.getTaskFormData(taskId);
		item.setFormData(formData);

		// 查询业务数据，一方面要把业务数据传递给JSP，给JSP的表单使用。另外一方面要生成JSON给静态表单使用。
		// 给静态表单使用的JSON，直接输出到JS代码里面，声明一个名为businessData的变量。
		// 传递给JSP使用的，那么需要把businessData放到名为td的对象里面。
		BusinessData data = getBusinessData(item.getDefinition(), item.getInstance().getBusinessKey());
		item.setBusinessData(data);

		return item;
	}

	@SuppressWarnings("unchecked")
	private BusinessData getBusinessData(ProcessDefinition definition, String businessKey) {
		try {
			// 1.根据FormService查询所有开始事件的表单属性，分别得到业务数据的类名和DAO的类名
			StartFormData formData = this.formService.getStartFormData(definition.getId());
			List<FormProperty> props = formData.getFormProperties();

			String businessDataDaoClassName;
			try {
				businessDataDaoClassName = props.stream()// 转换为流式API
						// filter等同于前面的if判断
						.filter((p) -> p.getId().equals("businessDataDaoClassName"))
						// 找到第一个结果，返回的是Optional类型的对象，通过get方法获取返回的值
						.findFirst().get()
						// 返回的值就是FormProperty，所以直接调用getValue()方法
						.getValue();
			} catch (NullPointerException e) {
				LOG.debug("{}流程没有配置业务数据对象的DAO实现类名，无法保存业务数据", definition.getName());
				return null;
			}

			// 3.找到DAO对象，调用save方法
			// 需要获取Spring的容器，才能获取DAO对象，所以当前类需要实现ApplicationContextAware接口
			Class<? extends BusinessDataRepository<?>> businessDataDaoClass;
			try {
				businessDataDaoClass = (Class<? extends BusinessDataRepository<?>>) Class
						.forName(businessDataDaoClassName);
			} catch (ClassNotFoundException e) {
				LOG.error("{}流程配置的业务数据DAO类无法加载，无法保存业务数据", definition.getName());
				LOG.debug(e.getMessage(), e);
				return null;
			}
			BusinessDataRepository<? extends BusinessData> dao = this.applicationContext.getBean(businessDataDaoClass);

			BusinessData data = dao.findById(businessKey);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void complete(String taskId, Map<String, String[]> params) {
		Map<String, Object> variables = new HashMap<>();
		params.forEach((key, values) -> {
			if (values.length == 1) {
				variables.put(key, values[0]);
			} else {
				variables.put(key, values);
			}
		});

		// 1.查询Task对象
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2.查询流程实例和路程定义
		ProcessDefinition definition = this.getProcessDefinitionById(task.getProcessDefinitionId());
		HistoricProcessInstance instance = this.historyService//
				.createHistoricProcessInstanceQuery()//
				.processInstanceId(task.getProcessInstanceId())//
				.singleResult();

		// 3.更新业务数据
		this.saveBusinessData(definition, params);

		// 4.完成任务
		this.taskService.complete(taskId, variables);
		// 5.记录操作信息
	}
}
