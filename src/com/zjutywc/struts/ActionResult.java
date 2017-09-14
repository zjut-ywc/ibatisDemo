package com.zjutywc.struts;
/**
 * ���ؽ������Ϣ
 */
public final class ActionResult {
	/**
	 * ִ��ʱ�Ƿ��д�������쳣
	 * Ĭ��û�д�
	 */
	private boolean error = false;
	/**
	 * ����Ĵ�����Ϣ
	 */
	private String errorMessage = null;
	
	private String JSONData = null;
	
	public boolean isError() {
		return error;
	}
	
	public void setError(String message) {
		error = true;
		if(message!=null){
			this.errorMessage = message;
		}
	}
	
	public void setError(String message, Exception exception){
		error = true;
		StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append("An exception of type \"");
        detailBuilder.append(exception.getClass().getName());
        detailBuilder.append("\" was thrown.");
        String exMsg = exception.getMessage();
        if(exMsg != null){
            detailBuilder.append("  The message is: ");
            detailBuilder.append(exMsg);
        }
        this.errorMessage = detailBuilder.toString();
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getJSONData() {
		return JSONData;
	}

	public void setJSONData(String data) {
		JSONData = data;
	}
}