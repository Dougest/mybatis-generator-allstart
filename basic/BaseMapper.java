package com.agilers.basic;

import java.util.List;
import java.util.Map;

/**
 * @Descripe
 * @author @Dougest
 * @Date 2018年9月28日 上午10:43:50
 */
public interface BaseMapper<T> {
	int deleteByPrimaryKey(String id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	List<T> queryList(Map<String, Object> map);

	int countList(Map<String, Object> map);

	List<T> queryListNoPage(Map<String, Object> map);
}
