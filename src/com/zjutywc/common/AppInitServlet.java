package com.zjutywc.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title:WebAgent Initialization
 * </p>
 * <p>
 * Description: Business init Process when init application
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sunyard
 * </p>
 * 
 * @author qiu jiehua
 * @version 1.0
 */

public class AppInitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger log = Logger.getLogger(AppInitServlet.class);

	/**
	 * Initialize global variables
	 * 
	 * @exception ServletException
	 */
	public void init() throws ServletException {

		/*
		 * 1.初始化 环境变量
		 */

		InitFun initF = new InitFun();
		initF.initXmlConfigFun(null, this.getServletContext());
	}

	/**
	 * Clean up resources
	 */
	public void destroy() {
	}

}