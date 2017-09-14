package com.zjutywc.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zjutywc.common.JSONUtil;

public class TestAction extends BaseAction {
	static Logger logger = Logger.getLogger(TestAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		super.execute(request, response);
		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String type = request.getParameter("type");
		String result = "";
		if("select".equals(type)){
			result = this.select(request,response);
		}
		return result;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
	}

	public void destroy() {
	}
	
	private String select(HttpServletRequest request, HttpServletResponse response){
		String result = "";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		List list = this.service.getSqlExecuteDao().queryListSqlInfo("select", map);
		result = JSONUtil.listToJSON(list);
		return result;
	}
}
