package org.mybatis.generator.api;

public final class ConstKey {
	public static final Boolean isPrint = true;
	public static final String service_reference = "mapper";
	public static final String SERVICE_ADD = "add";// 增加
	public static final String SERVICE_UPDATE = "update";// 更新
	public static final String SERVICE_DELETE = "delete";// 删除
	public static final String SERVICE_INFO = "info";// 详情
	public static final String SERVICE_LIST = "commonList";// 分页列表
	public static final String SERVICE_OLIST = "queryList";// object list

	public static final String SUCCESS_MSG = "操作成功!";
	public static final String FAIL_MSG = "操作失败!";

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

	/**
	 * 使用restfull风格接口 规范化接口地址
	 */
	public static final String PARAM_ADD = "add";// 增加
	public static final String PARAM_UPDATE = "update";// 更新
	public static final String PARAM_DELETE = "delete";// 删除
	public static final String PARAM_INFO = "info";// 详情
	public static final String PARAM_LIST = "list";// 分页列表
	public static final String PARAM_LIST_NO_PAGE = "nplist";// 未分页列表
	public static final String PARAM_KV_LIST = "kvlist";// 键值对列表

	// 针对miniui datagrid的空值查询结果
	public static final String RESULT_LIST_NULL = "{\\\"total\\\":\\\"0\\\",\\\"data\\\":[]}";
	// 空数组
	public static final String RESULT_ARRAY_NULL = "[]";
	// 空对象
	public static final String RESULT_OBJ_NULL = "{}";
}
