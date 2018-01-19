package com.wish.dao.util;

import com.wish.model.vo.PageInfo;

public interface Dialect {
	
	@SuppressWarnings("unused")
	public static enum Type {
		MYSQL {
			public String getValue() {
				return "mysql";
			}
		},
		ORACLE {
			public String getValue() {
				return "oracle";
			}
		}
	}

	public abstract String getPageSql(String sql, PageInfo page);
}
