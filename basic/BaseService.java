package com.agilers.basic;

import java.util.List;
import java.util.Map;

/**
 * @Descripe
 * @author @Dougest
 * @Date 2018年9月28日 上午10:44:12
 */
public interface BaseService<T> {
	/** 根据id删除表数据 **/
	int deleteByPrimaryKey(String id);

	/** 插入实体数据到表 **/
	int insert(T record);

	/** 插入局部实体数据到表 **/
	int insertSelective(T record);

	/** 主键查询实体数据 **/
	T selectByPrimaryKey(String id);

	/** 根据主键更新局部实体数据 **/
	int updateByPrimaryKeySelective(T record);

	/** 根据主键更新实体数据 **/
	int updateByPrimaryKey(T record);

	/** 解析传入的data数据,分页查询 **/
	String commonList(String data, int pageIndex, int pageSize);

	/** 根据插入的数据查询列表 **/
	List<T> queryList(Map<String, Object> map);
}
