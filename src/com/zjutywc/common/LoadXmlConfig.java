/*
 * @(#)LoadXmlConfig.java  1.0 2010-05-07
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * Title: ��ʼ��������<br />
 * 
 * @author ??
 * @version 1.0
 * 
 *          <pre>
 *    history:
 *    Version    Date        Author       Description 
 *    Ver.1.0.0  ??          ??           Created
 *    Ver.1.1.0  2010/05/07  gjj          Amended
 * </pre>
 */
public class LoadXmlConfig {
	static Logger log = Logger.getLogger(LoadXmlConfig.class);

	private Document doc;

	private String type = "";

	ServletContext ctext;

	private String file_path = "";

	/**
	 * 
	 * ���캯��
	 * 
	 * 
	 * @param type
	 *            ���NULL��LOAD�������ã�����о����Ӧ����ֻLOAD��Ӧ����
	 * @param ctext
	 * @param file
	 */
	public LoadXmlConfig(String type, ServletContext ctext, String file) {
		this.type = type;
		this.ctext = ctext;
		this.file_path = file;

	}

	/**
	 * ��ȡXML�����ļ�����ʼ������
	 * 
	 * @throws Exception
	 */
	public static String getParamValueFromContext(String skey, String mkey,
			ServletContext context) {

		Hashtable<String, String> attribute = (Hashtable<String, String>) context
				.getAttribute(mkey);
		if (attribute != null)
			return (String) attribute.get(skey);
		return "";

	}

	/**
	 * ��ȡXML�����ļ�����ʼ�����ݣ��ʺ��ڵ���������
	 * 
	 * @throws Exception
	 */
	public void initXmlConfig() throws Exception {
		File file = new File(file_path);
		if (!file.exists()) {
			return;
		}
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(file);
		} catch (Exception e) {
			log.error(e);
			return;
		} finally {
			builder = null;
			file = null;
		}
		if (doc != null) {
			Element root = doc.getRootElement();
			if (root != null) {
				List lit = root.getChildren("item");
				if (lit != null) {
					for (int i = 0; i < lit.size(); i++) {
						Element element = (Element) lit.get(i);
						String ename = element.getAttributeValue("name");
						if (type == null || ename.equals(type)) {
							this.loadMappingXmlConfig(element);
						}
					}
				}
			}
		}
	}

	/**
	 * ��ȡ��Ӧ����,�����ص��ڴ�
	 * 
	 * @param element
	 *            ��Ӧ���õ�ELEMENT
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	private void loadMappingXmlConfig(Element element) throws Exception {

		Hashtable hs = new Hashtable();
		String ename = element.getAttributeValue("name");

		List eleArr = element.getChildren("property");
		for (int j = 0; j < eleArr.size(); j++) {
			Element ele = (Element) eleArr.get(j);
			String name = ele.getAttributeValue("name");
			String value = ele.getAttributeValue("value");

			hs.put(name, value);
		}
		if (ctext != null) {
			ctext.setAttribute(ename, hs);
		}

	}

	/**
	 * �޸��ƶ���ӦKEY��XMLģ��
	 * 
	 * @param key
	 * @param hs
	 *            �޸Ĳ���
	 * @param file
	 *            �ļ�ȫ·��
	 * @return
	 * @throws Exception
	 */

	public boolean EditMappingXmlConfig(String key, Hashtable hs)
			throws Exception {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(file_path);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);

			Element root = doc.getRootElement(); //
			List monitors = root.getChildren(); //

			for (int i = 0; i < monitors.size(); i++) {
				Element ee = (Element) monitors.get(i);

				String ename = ee.getAttributeValue("name");
				if (key.equals(ename)) {
					List eleArr = ee.getChildren("property");
					for (int j = 0; j < eleArr.size(); j++) {
						Element ele = (Element) eleArr.get(j);
						String name = ele.getAttributeValue("name");
						ele.getAttribute("value").setValue(
								(String) hs.get(name));

					}
				}

			}
			String indent = "\n";
			boolean newLines = true;
			XMLOutputter outp = new XMLOutputter();
			fo = new FileOutputStream(file_path);
			outp.output(doc, fo);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			try {
				fi.close();
				fo.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}

}
