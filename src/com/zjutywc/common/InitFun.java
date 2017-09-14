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
 * Title: ��ʼ��������<br />
 * Description: ����webagent��ʼ��ʱ��������,���õ���Ϣ<br />
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
	 * ��־����
	 */
	static Logger log = Logger.getLogger(InitFun.class);
	public String CONFIGPATH = "/WEB-INF/systemConfig.xml";

	/**
	 * 1.��ʼ��XML����
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
			log.error("��ʼ��systemConfig�����ļ�����ʧ��" + e.getMessage(), e);
			return false;
		}
		log.info("��ʼ��systemConfig�����ļ����ݳɹ�");
		return true;
	}

}