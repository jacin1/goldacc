/*
 * @(#)Page.java 1.0 2011-6-23上午11:43:39
 *
 */
package com.xsyi.core.mybatis;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2011-6-23
 * @since framework
 * 
 */
public interface Page {
	
	public static int MIN_ACTIVE_PAGE = 1;//从1 开始
	public static int MIN_PAGE_SIZE = 1;
	public static int DEFAULT_PAGE_SIZE = 20;
	public static int ZERO_TOTAL_COUNT = 0;

	public static String PAGED_QUERY_SQL = "SELECT * FROM (SELECT T.*, ROWNUM ROWNUM_ FROM ( $SQL ) T  WHERE ROWNUM < $LASTRESULT) WHERE ROWNUM_ >= $FIRSTRESULT";
	public static String COUNT_SQL = "SELECT COUNT(1) FROM ($SQL)";

	/**
	 * 选择当前页
	 * @param activePage
	 * @return 链式操作，返回当前页对象
	 */
	public abstract Page selectPage(int activePage);

	/**
	 * 是否有后一页
	 * @return
	 */
	public abstract boolean hasNextPage();

	/**
	 * 是否有前一页
	 * @return
	 */
	public abstract boolean hasPrevPage();

	/**
	 * 得到后一页
	 * @return
	 */
	public abstract int getNextPageNO();

	/**
	 * 得到前一页
	 * @return
	 */
	public abstract int getPrevPageNO();



	/**
	 * 得到当前页第一条记录在所有记录中的位置
	 * @return
	 */
	public abstract int getFirstResult();

	/**
	 * 得到当前页最后一条记录在所有记录中的位置
	 * @return
	 */
	public abstract int getLastResult();

	/**
	 * 设置分页大小
	 * @param pageSize
	 */
	public abstract void setPageSize(int pageSize);

	/**
	 * 得到分页大小
	 * @return
	 */
	public abstract int getPageSize();

	/**
	 * 设置当前页
	 * @param activePage
	 */
	public abstract void setActivePage(int activePage);

	/**
	 * 得到当前页
	 * @return
	 */
	public abstract int getActivePage();
	
	/**
	 * 得到总页数
	 * @return
	 */
	public abstract int getTotalPages();

	/**
	 * 设置总记录数
	 * @param totalCount
	 */
	public abstract void setTotalCount(int totalCount);

	/**
	 * 得到总记录数
	 * @return
	 */
	public abstract int getTotalCount();

}