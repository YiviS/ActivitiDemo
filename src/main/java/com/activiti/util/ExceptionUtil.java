package com.activiti.util;
/**
 * @Author XuGuang
 * @Date 2016-7-4 11:59
 * @Desc 异常信息处理类
 */

public class ExceptionUtil {
	
	protected ExceptionUtil(){}
	
	/**
	 * 获取错误信息
	 * @param //stackTraceElement = ExceptionUtil.getThread().getStackTrace()
	 * @param e = Exception
	 * ExceptionUtil.getErrorInfo(ExceptionUtil.getThread().getStackTrace(), e)
	 */
	public static String getErrorInfo(Exception e){
		StackTraceElement[] stackTraceElement = e.getStackTrace();
		String errCont = "";
		if(e!=null){
			errCont += "异常信息："+e+"\n";
		}
		if(stackTraceElement!=null&&stackTraceElement.length>1){
			errCont += "异常位置：";
			for(int i=0;i<stackTraceElement.length;i++){
				if(stackTraceElement[i].toString().indexOf("Unknown Source")<0){
					errCont += stackTraceElement[i];
					break;
				}
			}
		}
		return errCont;
	}
}