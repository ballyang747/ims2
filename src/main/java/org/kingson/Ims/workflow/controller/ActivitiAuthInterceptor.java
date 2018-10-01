package org.kingson.Ims.workflow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.identity.Authentication;
import org.kingson.Ims.identity.domain.User;
import org.kingson.commrs.ConstantUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ActivitiAuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		User user = (User) request.getSession().getAttribute(ConstantUtil.SESSION_USER);
		if (user != null) {
			Authentication.setAuthenticatedUserId(user.getUserId());
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 清理现场
		Authentication.setAuthenticatedUserId(null);
		super.postHandle(request, response, handler, modelAndView);
	}
}
