package com.then.core.entity.page;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageResult<T> extends PageParam {

	private static final long serialVersionUID = -5807019797400360794L;

	private int totalPage;
	private long totalCount;
	private List<T> dataList;
}
