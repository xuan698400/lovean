package com.xuan.lovean.http.helper;

/**
 * http的回调函数
 * 
 * @author xuan
 */
public interface AjaxCallBack {

	/**
	 * 回调方法
	 * 
	 * @param isSuccess
	 *            表示是否请求成功
	 * @param result
	 *            返回的结果
	 * @param e
	 *            如果出现异常传入异常
	 */
	public void callBack(boolean isSuccess, String result, Exception e);
}
