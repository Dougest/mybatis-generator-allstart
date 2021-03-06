package basic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

/**
 * @Descripe
 * @author @Dougest
 * @Date 2018年9月28日 上午10:43:19
 */
public class BaseController<T> {
	public static final String LOGIN_USER = "user";
	public static final String SESSION_VERIABLE = "variable";
	public static final String SUCCESS_MSG = "操作成功!";
	public static final String FAIL_MSG = "操作失败!";
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
	public static final String RESULT_LIST_NULL = "{\"total\":\"0\",\"data\":[]}";
	// 空数组
	public static final String RESULT_ARRAY_NULL = "[]";
	// 空对象
	public static final String RESULT_OBJ_NULL = "{}";

	public static final String RESULT_OPER_SUCCESS = "{\"status\": \"true\",\"msg\": \"操作成功!\"}";

	public static final String RESULT_OPER_FAUILTRUE = "{\"status\": \"true\",\"msg\": \"操作失败!\"}";

	public static final Map<String, Object> RESULT_OPER_SUCCESS_JSON = new HashMap<String, Object>();
	public static final Map<String, Object> RESULT_OPER_FAUILTRUE_JSON = new HashMap<String, Object>();

	static {
		RESULT_OPER_SUCCESS_JSON.put("status", true);
		RESULT_OPER_SUCCESS_JSON.put("msg", "操作成功!");
		RESULT_OPER_FAUILTRUE_JSON.put("status", false);
		RESULT_OPER_FAUILTRUE_JSON.put("msg", "操作失败!");
	}

	public static final String NUMBER_ZERO = "0";

	/**
	 * 获取全局Session
	 */
	public static Object findUserFromSession() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			Object obj = WebUtils.getSessionAttribute(request, SESSION_VERIABLE);
			return obj != null ? obj : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 存入session
	public static boolean putUserFromSession(HttpServletRequest request, String name, Object value) {
		try {
			WebUtils.setSessionAttribute(request, name, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
