/*
 * @(#)InitFun.java  1.0 2010-05-07
 *
 * Copyright (c) 2010 Sunyard System Engineering Co., Ltd.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * Sunyard System Engineering Co., Ltd. ("Confidential Information").  
 * You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered 
 * into with Sunyard.
 */
package com.zjutywc.common;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

/**
 * Title: 初始化功能类<br />
 * Description: 用于webagent初始化时加载数据,配置等信息<br />
 * 
 * @author zhangshan
 * @version 1.1
 * 
 *          <pre>
 *    history:
 *    Version    Date        Author       Description 
 *    Ver.1.0.0  ??          zhangshan    Created
 *    Ver.1.1.0  2010/05/07  gjj          Amended
 *          </pre>
 */
public class InitFun {
	/*
	 * 日志对象
	 */
	static Logger log = Logger.getLogger(InitFun.class);
	public String CONFIGPATH = "/WEB-INF/systemConfig.xml";

	/**
	 * 1.初始化XML配置
	 * 
	 * @param ctext
	 * @return
	 */
	public boolean initXmlConfigFun(String type, ServletContext ctext) {
		try {
			LoadXmlConfig lc;
			lc = new LoadXmlConfig(type, ctext, ctext.getRealPath("") + CONFIGPATH);
			lc.initXmlConfig();

		} catch (Exception e) {
			log.error("初始化systemConfig配置文件数据失败" + e.getMessage(), e);
			return false;
		}
		log.info("初始化systemConfig配置文件数据成功");
		return true;
	}

}