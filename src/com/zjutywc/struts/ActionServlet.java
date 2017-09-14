package com.zjutywc.struts;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
 

 



public class ActionServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(ActionServlet.class);
	
	private static final String REQUEST_PROCESSOR_KEY ="com.hlw.struts.action.REQUEST_PROCESSOR";
	

 
	
	public void init(){
       
	}
	/*
	 * release resource
	 */
	public void destroy() {
		/*delete all action object instance*/
        this.getProcessorForModule().destroy();

        ServletContext context = getServletContext();
		Enumeration names = context.getAttributeNames();
		while(names.hasMoreElements()){
			context.removeAttribute((String)names.nextElement());
		}
	}
	
	private RequestProcessor getProcessorForModule() {
		return (RequestProcessor) getServletContext().getAttribute(REQUEST_PROCESSOR_KEY);
    }
	/*
	 * the only object to control request 
	 * only one instance
	 */
	private synchronized RequestProcessor getRequestProcessor(HashMap<String, String> config){
		RequestProcessor processor = this.getProcessorForModule();
		if (processor == null) {
			processor = new RequestProcessor();
			processor.initProcess(config);
            getServletContext().setAttribute(REQUEST_PROCESSOR_KEY, processor);
		}
		return (processor);
	}
	
	/*
	 * Perform the standard request processing
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response) {
		Hashtable<String, String> packageNames = (Hashtable<String, String>)getServletContext().getAttribute("ACTIONPGNAMECONFIG");
		
		HashMap<String, String> config = null;
		
		getRequestProcessor(config).process(request, response,packageNames);
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		process(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		try{
			process(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
