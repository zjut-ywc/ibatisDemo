package com.zjutywc.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zjutywc.service.Service;
import com.zjutywc.struts.Action;

public class BaseAction implements Action{
	
	Service service = null;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		ServletContext sc = request.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
		service = (Service) wac.getBean("Service");
		return "";
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
