package basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * @Descripe
 * @author @Dougest
 * @Date 2018年9月28日 上午10:43:35
 */
public class BaseServiceImpl<T> {
	/**
	 * 有效状态
	 */
	protected static final String YXZT_YES = "1";
	protected static final String YXZT_NO = "0";

	protected String pager4Mysql(int pageIndex, int pageSize, String data, BaseMapper<T> mapper) {
		int offest = 0;
		int limit = 10;

		Map<String, Object> map = commonsAnalyze(pageIndex, pageSize, data);
		// net.sf.json.JSONObject
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", mapper.countList(map));

		offest = Integer.valueOf(map.get("offset").toString());
		limit = Integer.valueOf(map.get("limit").toString());

		jsonObject.put("data", mapper.queryList(map, offest, limit));
		return jsonObject.toString();
	}

	protected String pager4Oracle(int pageIndex, int pageSize, String data, BaseMapper<T> mapper) {

		Map<String, Object> map = commonsAnalyze(pageIndex, pageSize, data);
		// net.sf.json.JSONObject
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", mapper.countList(map));
		jsonObject.put("data", mapper.queryList(map));
		return jsonObject.toString();
	}

	/**
	 * json 字符串转实体类
	 * 
	 * @auther Dougeset
	 * @create_time 2018年7月25日 下午4:11:26
	 * @describe:
	 */
	protected Object JsonStrToEntity(String json, Class<?> clz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object obj = JSONObject.toBean(jsonObject, clz);
		return obj;
	}

	/**
	 * json 字符串转实体类,解决转成实体后传入时间为当前时间问题
	 * 
	 * @auther dbl
	 * @create_time 2018年7月31日 下午2:43:26
	 * @describe:
	 */
	protected Object JsonStrToEntity2(String json, Class<?> clz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
		Object obj = JSONObject.toBean(jsonObject, clz);
		return obj;
	}

	/**
	 * @auther Dougeset
	 * @create_time 2018年8月2日 下午4:29:55
	 * @describe: json为空时直接返回空集合 JSONArray为空时直接返回空结合 本方法没有针对miniui的时间类型进行处理
	 *            如果需要,请自行重载本方法
	 * 
	 *            本方法测试及使用见下面main方法
	 */
	protected List<Object> jsonToList(String json, Class<?> clz) {
		if (isBlank(json))
			return new ArrayList<Object>();

		JSONArray jsonArray = JSONArray.fromObject(json);
		if (jsonArray == null || jsonArray.isEmpty())
			return new ArrayList<Object>();

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = jsonArray.getJSONObject(i);
			if (jo.isEmpty())
				break;
			Object obj = JSONObject.toBean(jo, clz);
			list.add(obj);
		}
		return list;
	}

	/**
	 * @Describe 解析分页参数成map
	 * @Author Dougest
	 * @Date 2018年9月28日 上午10:49:54
	 * @param pageIndex
	 * @param pageSize
	 * @param reqJson
	 * @return
	 */
	protected Map<String, Object> commonsAnalyze(int pageIndex, int pageSize, String reqJson) {

		Map<String, Object> map = this.JsonStrTOMap(reqJson);

		// mysql
		String offset = String.valueOf(pageIndex * pageSize);
		String limit = String.valueOf(pageSize);
		map.put("offset", offset);
		map.put("limit", limit);

		// oracle
		String start = String.valueOf((pageIndex - 1) * pageSize);
		String end = String.valueOf(pageIndex * pageSize);
		map.put("start", start);
		map.put("end", end);

		String time;
		Date date;

		if (map.get("startTime") != null) {
			time = map.get("startTime").toString();
			if (time.length() > 0) {
				time = time.replaceAll("T", " ");
				date = this.stringParseDate(time, "yyyy-MM-dd HH:mm:ss");
				map.put("startTime", date);
			} else
				map.put("startTime", null);

		}
		if (map.get("endTime") != null) {
			time = map.get("endTime").toString();
			if (time.length() > 0) {
				time = time.replaceAll("T", " ");
				date = this.stringParseDate(time, "yyyy-MM-dd HH:mm:ss");
				map.put("endTime", date);
			} else
				map.put("endTime", null);
		}
		return map;
	}

	/**
	 * @Describe 解析string成map
	 * @Author Dougest
	 * @Date 2018年9月28日 上午10:50:16
	 * @param jsonStr
	 * @return
	 */
	protected Map<String, Object> JsonStrTOMap(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonStr == null || jsonStr.length() < 1)
			return map;
		else {
			JSONObject json = JSONObject.fromObject(jsonStr);
			if (json == null || json.isEmpty())
				return map;
			@SuppressWarnings("unchecked")
			Set<Map.Entry<String, Object>> sets = json.entrySet();
			Iterator<Map.Entry<String, Object>> itera = sets.iterator();
			while (itera.hasNext()) {
				Entry<String, Object> entry = itera.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				map.put(key, value);
			}
			return map;
		}
	}

	/**
	 * @Describe string转date
	 * @Author Dougest
	 * @Date 2018年9月28日 上午10:50:37
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	protected Date stringParseDate(String dateStr, String pattern) {
		if (dateStr == null || dateStr.length() < 1) {
			return new Date();
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				return sdf.parse(dateStr);
			} catch (ParseException arg3) {
				arg3.printStackTrace();
				return new Date();
			}
		}
	}

	/**
	 * @Describe clear map,put new k-v
	 * @Author Dougest
	 * @Date 2018年10月12日 上午10:47:21
	 * @param map
	 * @param key
	 * @param value
	 * @return
	 */
	protected static Object putNewValue(Map<String, Object> map, String key, Object value) {
		map.clear();
		map.put(key, value);
		return map.get(key);
	}

	/**
	 * 参数为null或者""," ",返回true,即有空值参数
	 * 
	 * @Describe
	 * @Author Dougest
	 * @Date 2PASS_STATUS18年1PASS_STATUS月12日 上午9:49:PASS_STATUS9
	 * @param str
	 * @return
	 */
	protected boolean vaildNotNullParam(String... str) {
		for (String s : str) {
			if (isBlank(s))
				return true;
		}
		return false;
	}

	public static boolean isBlank(CharSequence cs) {
		int len;
		if (cs == null || (len = cs.length()) == 0)
			return true;

		for (int i = 0; i < len; i++)
			if (Character.isWhitespace(cs.charAt(i)) == false)
				return false;

		return true;
	}

	protected static Map<String, Object> getNewMapInstance() {
		return new HashMap<String, Object>();
	}

	protected void addYxzt4Map(Map<String, Object> map) {
		map.put("yxzt", YXZT_YES);
	}
}
