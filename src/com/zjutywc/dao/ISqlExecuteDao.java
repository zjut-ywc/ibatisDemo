package com.zjutywc.dao;

import java.util.HashMap;
import java.util.List;

public interface ISqlExecuteDao {
	public abstract boolean updateSqlInfo(String xmlid, HashMap hs);

	public abstract int insertIntSqlInfo(String xmlid, HashMap hs);

	public abstract boolean insertBoolSqlInfo(String xmlid, HashMap hs);

	public abstract boolean deleteSqlInfo(String xmlid, HashMap hs);

	public abstract Object queryObjectSqlInfo(String xmlid, HashMap hs);

	public abstract List<Object> queryListSqlInfo(String xmlid, HashMap hs);

	public abstract List<Object> queryListPageSqlInfo(String xmlid, HashMap hs);

	public abstract boolean rollbackForsql(List sql,
			HashMap<String, HashMap> hs, HashMap<Object, Object> hs_operate);

	public abstract boolean rollbackForsql(String id1, HashMap hs, String id2,
			HashMap hs2);

	public abstract int batchInsertIbatisSpring(String xmlid, List<Object> list);

	public abstract int batchUpdateIbatisSpring(String xmlid, List<Object> list);

	public abstract int batchDeleteIbatisSpring(String xmlid, List<Object> list);

	public abstract void batchInsert(final String sqlid, final List<Object> List);

	public abstract void batchUpdate(final String sqlid, final List<Object> List);

	public abstract void batchDelete(final String sqlid, final List<Object> List);

}
