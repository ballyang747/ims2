package org.kingson.commrs.pager;

/**
 * alt+shift+j
 * 
 * @author kingson 2018年7月31日 org.kingson.commrs.pager Imsn
 * @version 1.0
 * 
 * @email 1606656167@qq.com
 * @tel 15768607477
 * 
 */
public class PageModel {

	/** 分页中默认一个4条数据 */

	/** 分页总数据条数recordCount */
	private int recordCount;
	/** 当前页面 */
	private int pageIndex = 1;
	/** 每页分多少条数据 */
	private int pageSize = 4;

	/** 总页数 */
	private int totalSize;

	public int getRecordCount() {
		this.recordCount = this.recordCount <= 0 ? 0 : this.recordCount;
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageIndex() {
		int totalPageNum = this.recordCount % this.pageSize == 0 ? this.recordCount / this.pageSize
				: this.recordCount / this.pageSize + 1;

		// 限制最少1页
		if (totalPageNum == 0) {
			totalPageNum = 1;
		}

		/** 判断当前页面是否超过了总页数:如果超过了默认给最后一页作为当前页 */

		return pageIndex > totalPageNum ? totalPageNum : pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		if (this.getRecordCount() <= 0) {
			totalSize = 0;
		} else {
			totalSize = (this.getRecordCount() - 1) / this.getPageSize() + 1;
		}
		return totalSize;
	}

	public int getFirstLimitParam() {
		return (this.getPageIndex() - 1) * this.getPageSize();
	}

}
