package com.zjutywc.service;

import javax.servlet.ServletContext;

import com.zjutywc.common.LoadXmlConfig;
import com.zjutywc.dao.ISqlExecuteDao;

public class Service {
	ISqlExecuteDao sqlExecuteDao;
	
	public static String getParamValueFromContext(String skey, String mkey,
			ServletContext context) {
		return LoadXmlConfig.getParamValueFromContext(skey, mkey, context);
	}

	public ISqlExecuteDao getSqlExecuteDao() {
		return sqlExecuteDao;
	}

	public void setSqlExecuteDao(ISqlExecuteDao sqlExecuteDao) {
		this.sqlExecuteDao = sqlExecuteDao;
	}
	
}
