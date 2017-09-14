package com.zjutywc.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zjutywc.common.JSONUtil;

public class RequestProcessor {
	private static Logger logger = Logger.getLogger(RequestProcessor.class);
	
	

	private HashMap actions = new HashMap();

	private HashMap<String, String> moduleConfig = null;
	/**
	 * Clean up in preparation for a shutdown of this application
	 */
	protected void destroy() {

		synchronized (this.actions) {
			this.actions.clear();
		}
		moduleConfig = null;
	}

	/**
	 * Initialize this request processor instance
	 * init param
	 */
	protected void initProcess(HashMap<String, String> moduleConfig){

		synchronized (actions) {
			actions.clear();
		}

		this.moduleConfig = moduleConfig;
	}

	/*
	 * Process an HttpServletRequest and 
	 * create the corresponding HttpServletResponse
	 */
	protected void process(HttpServletRequest request, HttpServletResponse response,Hashtable<String, String> packageNames) {
		
		/*First get request path*/
		String path = processPath(request, response);
		if (path == null) { 
			return;
		}
		/*Second set out type default html*/
		response.setContentType("text/html;charset=GBK");
		/*Third not cache the out result*/
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 1);
		/*Forth get the object of  the Action*/
		Action action = processActionCreate(request,response,path,packageNames);
		if (action == null) {
		
			try {
				PrintWriter out = response.getWriter();
				out.write(JSONUtil.errorInjson("02","outofconnection","找不到对应的action["+path+"]"));
				out.flush();
			} catch (IOException e) {}
			
			return;
        }
		/*Last invoke the execute method of the action*/
		String outString = null;
		try {
			outString = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			outString = "系统出现异常,系统错误信息:"+e.getMessage();
		}finally{
			System.out.println("------------->"+outString);			
			String type = request.getParameter("type");
			System.out.println("------------->"+type);		
			if("excel".equals(outString)){
				
			}else if("downloadDate".equals(type)){
				
			}else {
				if(outString!=null){
					try {
						PrintWriter out = response.getWriter();
						out.print(outString);
						out.flush();
					} catch (IOException e) {}
				}	
			}
			
		}
	}
	
	/*
	 * 创建action对象
	 */
	private Action processActionCreate(HttpServletRequest request,
            HttpServletResponse response,String path,Hashtable<String, String> packageNames){
		
	//	String className = moduleConfig.get(path);
		String className = "" ;
		Action instance = null;
		synchronized (actions) {
			instance = (Action) actions.get(path);
			
            if (instance != null) {
          
                return (instance);
            }
            Iterator it = packageNames.keySet().iterator();
            System.out.println(packageNames.size());
            while(it.hasNext())
            {
            	 String packageName =packageNames.get((String)it.next());
            	 className = packageName+"."+path.substring(0,1).toUpperCase()+path.substring(1,path.length());
            	 logger.info("请求的class类为："+packageName+className);
            	
		            try {
		                instance = (Action) RequestUtils.applicationInstance(className);
		                if(instance != null)
		            	 {
		                	 actions.put(path, instance);
		                	 break;
		            	 }
		                     
		            } catch (Exception e) { 
		            	 logger.error(e);
		            }
            	  
            }
            logger.info("请求到的instance实例类为："+instance);
         
		}
		return (instance);
	}
	
	/*
	 * 取得action配制中的path
	 */
	private String processPath(HttpServletRequest request,
			HttpServletResponse response) {

		String path = request.getServletPath();

		int period = path.lastIndexOf(".");
		
		if (period >= 0) {
			path = path.substring(1, period);
		}
		return (path);
	}

}
