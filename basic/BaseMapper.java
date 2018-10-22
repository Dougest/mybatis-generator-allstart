package basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @Descripe
 * @author @Dougest
 * @Date 2018年9月28日 上午10:43:50
 */
public interface BaseMapper<T> {
	int deleteByPrimaryKey(Long id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	/** is suit for mysql **/
	List<T> queryList(@Param("params") Map<String, Object> map, @Param("offset") int offset, @Param("limit") int limit);

	/** is suit for oracle **/
	List<T> queryList(Map<String, Object> map);

	int countList(Map<String, Object> map);

	List<T> queryListNoPage(Map<String, Object> map);
}
