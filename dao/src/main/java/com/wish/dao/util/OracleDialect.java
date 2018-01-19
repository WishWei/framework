package com.wish.dao.util;


import com.wish.model.vo.PageInfo;

public class OracleDialect implements Dialect {

	public String getPageSql(String sql, PageInfo page) {
		if(null==page){
			return sql;
		}
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pageSql = new StringBuffer();
		pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
		pageSql.append(sql);
		pageSql.append(")  tmp_tb where ROWNUM<=");
		pageSql.append((page.getPage() - 1) * page.getPageSize() + page.getPageSize());
		pageSql.append(") where row_id>");
		pageSql.append((page.getPage() - 1) * page.getPageSize());
		if (isForUpdate) {
			pageSql.append(" for update");
		}
		return pageSql.toString();
	}

}
