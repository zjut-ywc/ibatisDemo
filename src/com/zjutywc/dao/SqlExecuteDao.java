package com.zjutywc.dao;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.zjutywc.common.GlobalKeyWord;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class SqlExecuteDao extends SqlMapClientDaoSupport implements
		ISqlExecuteDao {

	public boolean updateSqlInfo(String xmlid, HashMap hs) {
		int cc = 0;
		try {
			cc = getSqlMapClientTemplate().update(xmlid, hs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (cc > 0) {
			return true;
		} else {
			return false;
		}

	}

	public int insertIntSqlInfo(String xmlid, HashMap hs) {
		int retStr = -1;
		try {
			retStr = (Integer) getSqlMapClientTemplate().insert(xmlid, hs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}

	public boolean insertBoolSqlInfo(String xmlid, HashMap hs) {
		boolean successful = true;
		try {
			getSqlMapClientTemplate().insert(xmlid, hs);
		} catch (Exception ex) {
			successful = false;
			ex.printStackTrace();
		}
		return successful;
	}

	public boolean deleteSqlInfo(String xmlid, HashMap hs) {
		int cc = 0;
		try {
			cc = getSqlMapClientTemplate().delete(xmlid, hs);
		} catch (Exception e) {
			cc = -1;
			e.printStackTrace();
		}
		if (cc > 0) {
			return true;
		} else {
			return false;
		}

	}

	public Object queryObjectSqlInfo(String xmlid, HashMap hs) {
		Object object = null;
		object = getSqlMapClientTemplate().queryForObject(xmlid, hs);
		return object;
	}

	public List<Object> queryListSqlInfo(String xmlid, HashMap hs) {
		List<Object> list = null;
		list = getSqlMapClientTemplate().queryForList(xmlid, hs);
		return list;
	}

	public List<Object> queryListPageSqlInfo(String xmlid, HashMap hs) {
		List<Object> list = null;
		int start = 0;
		int limit = 0;

		start = Integer.parseInt((String) hs.get(GlobalKeyWord.PAGE_START));
		limit = Integer.parseInt((String) hs.get(GlobalKeyWord.PAGE_LIMIT));
		list = getSqlMapClientTemplate().queryForList(xmlid, hs, start, limit);
		return list;
	}

	/**
	 * 2条sql事物操作
	 * */
	@Transactional(rollbackFor = Exception.class)
	public boolean rollbackForsql(String id1, HashMap hs1, String id2,
			HashMap hs2) {
		int cc = 0;
		try {
			cc = getSqlMapClientTemplate().update(id1, hs1);
			cc = getSqlMapClientTemplate().update(id2, hs2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (cc > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Ibatis结合Spring批量数据新增
	 * 
	 * @author YED 2014-2-27
	 * @param xmlid
	 *            , List<Object>条件
	 * */
	public int batchInsertIbatisSpring(String xmlid, List<Object> list) {
		int insRows = 0;
		long batchStartTime = System.currentTimeMillis();
		try {
			SqlMapClient smc = this.getSqlMapClient();
			smc.startBatch();
			for (int i = 0; i < list.size(); i++) {
				this.insertIntSqlInfo(xmlid, (HashMap) list.get(i));
			}
			insRows = smc.executeBatch();

			logger.info("批量新增数据条数=[" + insRows + "]");
		} catch (Exception e) {
			insRows = -1;
			logger.info("批量新增异常[" + e.getMessage() + "]");
		} finally {
			try {
				this.getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		long batchEndTime = System.currentTimeMillis();
		logger.info("本次批量新增数据总耗时========[" + (batchEndTime - batchStartTime)
				+ "]");
		return insRows;
	}

	/**
	 * Ibatis结合Spring批量数据更新
	 * 
	 * @author YED 2014-2-27
	 * @param xmlid
	 *            , List<Object>条件
	 * */
	public int batchUpdateIbatisSpring(String xmlid, List<Object> list) {
		int upRows = 0;
		long batchStartTime = System.currentTimeMillis();
		try {
			SqlMapClient smc = this.getSqlMapClient();
			smc.startBatch();
			for (int i = 0; i < list.size(); i++) {
				this.updateSqlInfo(xmlid, (HashMap) list.get(i));
			}
			upRows = smc.executeBatch();

			logger.info("批量更新数据条数=[" + upRows + "]");
		} catch (Exception e) {
			upRows = -1;
			logger.info("批量更新异常[" + e.getMessage() + "]");
		} finally {
			try {
				this.getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		long batchEndTime = System.currentTimeMillis();
		logger.info("本次批量更新数据总耗时========[" + (batchEndTime - batchStartTime)
				+ "]");
		return upRows;
	}

	/**
	 * Ibatis结合Spring批量数据删除
	 * 
	 * @author YED 2014-2-27
	 * @param xmlid
	 *            , List<Object>条件
	 * */
	public int batchDeleteIbatisSpring(String xmlid, List<Object> list) {
		int delRows = 0;
		long batchStartTime = System.currentTimeMillis();
		try {
			SqlMapClient smc = this.getSqlMapClient();
			smc.startBatch();
			for (int i = 0; i < list.size(); i++) {
				this.deleteSqlInfo(xmlid, (HashMap) list.get(i));
			}
			delRows = smc.executeBatch();

			logger.info("批量删除影响数据条数=[" + delRows + "]");
		} catch (Exception e) {
			delRows = -1;
			logger.info("批量删除异常[" + e.getMessage() + "]");
		} finally {
			try {
				this.getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		long batchEndTime = System.currentTimeMillis();
		logger.info("本次批量删除数据总耗时========[" + (batchEndTime - batchStartTime)
				+ "]");
		return delRows;
	}

	/**
	 * Ibatis批量数据的插入
	 * 
	 * @param sqlid
	 *            xmlid
	 * @param List
	 *            list数据
	 */
	public void batchInsert(final String sqlid, final List<Object> List) {
		try {
			if (List != null) {
				this.getSqlMapClientTemplate().execute(
						new SqlMapClientCallback() {
							public Object doInSqlMapClient(
									SqlMapExecutor executor)
									  {
								try {
									executor.startBatch(); // 一定要有，通知开始批量
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									 
								}
								int batch = 0;
								for (int i = 0, n = List.size(); i < n; i++) {
									// 调用获取sequence的方法。如果没有的话就去掉这行代码。
									HashMap hs = (HashMap) List.get(i);
									// 参数1为：ibatis中需要执行的语句的id ，参数2为要插入的数据
									try {
										executor.insert(sqlid, hs);
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										//e1.printStackTrace();
									}
									batch++;
									// 每500条批量提交一次。
									//logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+i);
									if (batch == 500) {
										try {
											executor.executeBatch();
										} catch (BatchUpdateException e) {
											// TODO Auto-generated catch block
											logger.info("批处理插入失败，有重复数据存在");
											continue;
										}catch (SQLException e1) {
											// TODO Auto-generated catch block
											//e1.printStackTrace();
										}
										batch = 0;
									}
									//logger.info("继续执行！");
								}
								try {
									executor.executeBatch();
								}  catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} // 将最后的数据执行，最后不够500条的数据
								return null;
							}
						});
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("batchInsert error: id [" + sqlid + "],"
					+ " parameterObject  Cause: "
					+ e.getMessage());
		}
	}

	/**
	 * ibatis批量更新数据
	 * 
	 * @param sqlid
	 * @param List
	 */
	public void batchUpdate(final String sqlid, final List<Object> List) {
		try {
			if (List != null) {
				this.getSqlMapClientTemplate().execute(
						new SqlMapClientCallback() {
							public Object doInSqlMapClient(
									SqlMapExecutor executor)
									throws SQLException {
								executor.startBatch();
								int batch = 0;
								for (int i = 0, n = List.size(); i < n; i++) {
									HashMap hs = (HashMap) List.get(i);
									executor.update(sqlid, hs);
									batch++;
									// 每500条批量提交一次。
									if (batch == 500) {
										executor.executeBatch();
										batch = 0;
									}
								}
								executor.executeBatch(); // 将最后的数据执行，最后不够500条的数据
								return null;
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("batchUpdate error: id [" + sqlid + "], "
					+ "parameterObject [" + List + "].  Cause: "
					+ e.getMessage());
		}
	}

	/**
	 * ibatis批量删除数据
	 * 
	 * @param sqlid
	 * @param List
	 */
	public void batchDelete(final String sqlid, final List<Object> List) {
		try {
			if (List != null) {
				this.getSqlMapClientTemplate().execute(
						new SqlMapClientCallback() {
							public Object doInSqlMapClient(
									SqlMapExecutor executor)
									throws SQLException {
								executor.startBatch();
								int batch = 0;
								for (int i = 0, n = List.size(); i < n; i++) {
									HashMap hs = (HashMap) List.get(i);
									executor.delete(sqlid, hs);
									batch++;
									// 每500条批量删除一次。
									if (batch == 50) {
										executor.executeBatch();
										batch = 0;
									}
								}
								executor.executeBatch();
								return null;
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("batchDelete error: id [" + sqlid + "],"
					+ " parameterObject [" + List + "].  Cause: "
					+ e.getMessage());
		}
	}

	/* 事物统一操作 */
	@Transactional(rollbackFor = Exception.class)
	public boolean rollbackForsql(List sql, HashMap<String, HashMap> hs,
			HashMap<Object, Object> hs_operate) {
		boolean successfule = true;
		try {
			for (int i = 0; i < sql.size(); i++) {
				String listSql = (String) sql.get(i);
				HashMap conditionHs = hs.get(listSql);
				String operate_type = (String) hs_operate.get(listSql);
				if ("delete".equals(operate_type)) {
					getSqlMapClientTemplate().delete(listSql, conditionHs);
				} else if ("update".equals(operate_type)) {
					getSqlMapClientTemplate().update(listSql, conditionHs);
				} else if ("insert".equals(operate_type)) {
					getSqlMapClientTemplate().insert(listSql, conditionHs);
				}
			}
		} catch (Exception ex) {
			successfule = false;
			ex.printStackTrace();
		}
		return successfule;
	}

}
