package org.kingson.Ims.workflow.vo;

import org.activiti.engine.form.FormData;
import org.activiti.engine.repository.ProcessDefinition;

public class ProcessForm {

	private ProcessDefinition definition;
	private FormData formData;
	private String formKey;
	private Object formContent;

	public ProcessDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(ProcessDefinition definition) {
		this.definition = definition;
	}

	public FormData getFormData() {
		return formData;
	}

	public void setFormData(FormData formData) {
		this.formData = formData;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public Object getFormContent() {
		return formContent;
	}

	public void setFormContent(Object formContent) {
		this.formContent = formContent;
	}
}
