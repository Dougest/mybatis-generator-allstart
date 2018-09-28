package com.agilers.basic;

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

import com.agilers.utils.StringUtils;

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
		if (StringUtils.isBlank(json))
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
		String start = String.valueOf((pageIndex - 1) * pageSize + 1);
		String end = String.valueOf(pageIndex * pageSize);
		Map<String, Object> map = this.JsonStrTOMap(reqJson);
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
}
