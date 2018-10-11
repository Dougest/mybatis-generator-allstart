package org.mybatis.generator.api;

public final class ConstKey {
	public static final Boolean isPrint = true;
	public static final String service_reference = "mapper";
	public static final String SERVICE_ADD = "add";//
	public static final String SERVICE_UPDATE = "update";//
	public static final String SERVICE_DELETE = "delete";//
	public static final String SERVICE_INFO = "info";//
	public static final String SERVICE_LIST = "commonList";//
	public static final String SERVICE_OLIST = "queryList";// object list

	public static final String SUCCESS_MSG = "操作失败";
	public static final String FAIL_MSG = "操作成功!";

	public static String getFauilreData() {
		return "{\\\"status\\\": \\\"false\\\",\\\"msg\\\": \\\"" + FAIL_MSG + "\\\"}";
	}

	public static String returnFauilreData() {
		return "return \"" + getFauilreData() + "\";";
	}

	public static String getSuccessData() {
		return "{\\\"status\\\": \\\"true\\\",\\\"msg\\\": \\\"" + SUCCESS_MSG + "\\\"}";
	}

	public static String returnSuccessData() {
		return "return \"" + getSuccessData() + "\";";
	}

	public static final String PARAM_ADD = "add";//
	public static final String PARAM_UPDATE = "update";//
	public static final String PARAM_DELETE = "delete";//
	public static final String PARAM_INFO = "info";//
	public static final String PARAM_LIST = "list";//
	public static final String PARAM_LIST_NO_PAGE = "nplist";//
	public static final String PARAM_KV_LIST = "kvlist";//

	public static final String RESULT_LIST_NULL = "{\\\"total\\\":\\\"0\\\",\\\"data\\\":[]}";
	public static final String RESULT_ARRAY_NULL = "[]";
	public static final String RESULT_OBJ_NULL = "{}";
}
