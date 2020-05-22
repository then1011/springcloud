package com.then.core.entity.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageParam implements Serializable {

	private static final long serialVersionUID = -4159492966783350926L;

	private int pageNum = 1;
	private int pageSize = 10;
}
