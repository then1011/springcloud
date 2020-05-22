package com.then.db.page;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.then.core.entity.page.PageParam;
import com.then.core.entity.page.PageResult;
import com.then.core.enums.SysCodeEnum;
import com.then.core.exception.SystemException;

public class PageUtils {

	private PageUtils() {
	}

	public static <P extends PageParam, V> PageResult<V> generate(P param, QueryData<P, V> queryData) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<V> queryResult = queryData.execute(param);
		if(!(queryResult instanceof Page)) {
			throw new SystemException(SysCodeEnum.ILLEGAL_PARAMETER);
		}
		return generatePageResult(queryResult);
	}
	
	private static <V> PageResult<V> generatePageResult(List<V> result) {
		PageInfo<V> pageInfo = new PageInfo<>(result);
		PageResult<V> pageResult = new PageResult<V>();
		pageResult.setPageNum(pageInfo.getPageNum());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setTotalPage(pageInfo.getPages() == 0 ? 1 : pageInfo.getPages());
		pageResult.setTotalCount(pageInfo.getTotal());
		pageResult.setDataList(result);
		return pageResult;
	}
	
	@FunctionalInterface
	public interface QueryData<P extends PageParam, V>{
		List<V> execute(P param);
	}

}
