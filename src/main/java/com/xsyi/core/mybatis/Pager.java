/*
 * @(#)Pager.java 1.0 2011-6-23上午11:45:46
 *
 */
package com.xsyi.core.mybatis;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;


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
public class Pager extends RowBounds implements Page{

	private int activePage = MIN_ACTIVE_PAGE;
	private int totalCount = ZERO_TOTAL_COUNT;
	private int pageSize = DEFAULT_PAGE_SIZE;

	public Pager() {
	}

	public Pager(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#selectPage(int)
	 */
	@Override
	public Pager selectPage(int activePage) {
		this.activePage = activePage;
		return this;
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#hasNextPage()
	 */
	@Override
	public boolean hasNextPage() {
		return (getActivePage()<getTotalPages());
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#hasPrevPage()
	 */
	@Override
	public boolean hasPrevPage() {
		return (getActivePage()>MIN_ACTIVE_PAGE);
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getNextPageNO()
	 */
	@Override
	public int getNextPageNO() {
		if (hasNextPage()){
			return getActivePage()+1;			
		}else{
			return getActivePage();	
		}
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getPrevPageNO()
	 */
	@Override
	public int getPrevPageNO() {
		if (hasPrevPage()){
			return getActivePage()-1;
		}else{
			return getActivePage();
		}			
	}



	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getFirstResult()
	 */
	@Override
	public int getFirstResult() {
		if (getActivePage()<=MIN_ACTIVE_PAGE||getPageSize()<MIN_PAGE_SIZE){
			return 0;
		}else{
			return ((getActivePage()-1)*getPageSize());
		}
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getLastResult()
	 */
	@Override
	public int getLastResult() {
		if (hasNextPage()){
			return (getFirstResult() + getPageSize() - 1);
		}else{
			if(getTotalCount()%getPageSize() == 0 ){
				if(getTotalCount()==0){
					return ZERO_TOTAL_COUNT;
				}else{
					return (getTotalCount()-1);
				}
			}else{
				return (getFirstResult() + getTotalCount()%getPageSize()-1);
			}
		}
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#setPageSize(int)
	 */
	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getPageSize()
	 */
	@Override
	public int getPageSize() {
		return pageSize;
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#setActivePage(int)
	 */
	@Override
	public void setActivePage(int activePage) {
		this.activePage = activePage;
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getActivePage()
	 */
	@Override
	public int getActivePage() {
		return activePage;
	}
	
	
	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getTotalPages()
	 */
	@Override
	public int getTotalPages() {
		if (getTotalCount()<=ZERO_TOTAL_COUNT){
			return ZERO_TOTAL_COUNT;
		}
		int count = getTotalCount()/getPageSize();
		if (getTotalCount()%getPageSize()>0) {
			count++;
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#setTotalCount(int)
	 */
	@Override
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/* (non-Javadoc)
	 * @see core.repository.ibatis.Page#getTotalCount()
	 */
	@Override
	public int getTotalCount() {
		return totalCount;
	}




	/* (non-Javadoc)
	 * @see org.apache.ibatis.session.RowBounds#getOffset()
	 */
	@Override
	public int getOffset() {
		return getFirstResult();
	}
	
	/* (non-Javadoc)
	 * @see org.apache.ibatis.session.RowBounds#getLimit()
	 */
	@Override
	public int getLimit() {
		return getPageSize();
	}
	
	
	public RowBounds toRowBounds(){
		return new RowBounds(getFirstResult(), getPageSize());
	}
	


	
	public static String generateCountSql(String sql) {
		return StringUtils.replace(COUNT_SQL, "$SQL", sql);
	}

	public static String generatePagedQuerySql(String sql, int offset,int limit) {
				return StringUtils.replace(PAGED_QUERY_SQL, "$SQL", sql).replace("$FIRSTRESULT", offset+1+"").replace("$LASTRESULT",(offset+limit+1)+"");
	}
	
//	public static void main(String[] args) {
//		int LIMIT = 5;
//		ArrayList<Integer> a = new ArrayList<Integer>();
//		for(int i = 1; i<11; i++){
//			a.add(i);
//		}
//		Pager pager = new Pager(LIMIT);
//		pager.setTotalCount(a.size());
//		System.out.println("Pager方式: 每页"+LIMIT+"条");
//		System.out.println("total pages: "+pager.getTotalPages());
//		for(int i=0; i < pager.getTotalPages();i++,pager.setActivePage(pager.getNextPageNO())){
//			System.out.print("page: "+pager.getActivePage()+"; ");
//			System.out.print("first index: "+pager.getFirstResult()+"; ");
//			System.out.print("last index: "+pager.getLastResult()+"\n");
//			List<Integer> b = new ArrayList<Integer>();
//			for(int j=pager.getFirstResult();j<=pager.getLastResult();j++){
//				b.add(a.get(j));
//			}
//			for(int j: b){
//				System.out.print(j+"; ");
//			}
//			System.out.println();
//		}
//		System.out.println("------------");
//		System.out.println("常规方式： 每页"+LIMIT+"条");
//		//记录总数
//		int size=a.size();
//		 // 获得需要分组的组数
//		int groupNum = (size%LIMIT == 0) ? size/LIMIT : size/LIMIT +1 ;
//		System.out.println("total pages: "+groupNum);
//		// 循环所有的组
//        for (int i = 1; i <= groupNum; i++) {
//        	int begin = LIMIT * (i-1);
//			int end = LIMIT * i > size ? size-1 : LIMIT * i - 1;
//			System.out.print("page: "+i+"; ");
//			System.out.print("first index: "+begin+"; ");
//			System.out.print("last index: "+end+"\n");
//        	List<Integer> pageList = new ArrayList<Integer>();//每个分页里的列表
//			for (int j = begin; j <=end; j++) {
//				System.out.print(a.get(j)+"; ");
//				pageList.add(a.get(j));
//			}
//			System.out.println();
//        }
//	}
}