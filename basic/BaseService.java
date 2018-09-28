package basic;

import java.util.List;
import java.util.Map;

/**
 * @Descripe
 * @author @Dougest
 * @Date 2018年9月28日 上午10:44:12
 */
public interface BaseService<T> {
	int delete(Long id);

	int add(String data);

	T info(Long id);

	int update(String data);

	String commonList(int pageIndex, int pageSize, String data);

	List<T> queryList(Map<String, Object> map);
}
