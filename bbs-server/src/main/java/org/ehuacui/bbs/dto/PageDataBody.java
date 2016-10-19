package org.ehuacui.bbs.dto;


import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 */
public class PageDataBody<T> implements Serializable {

    private static final long serialVersionUID = -5395997221963176643L;

    private List<T> list;                // list result of this page
    private int pageNumber;                // page number
    private int pageSize;                // result amount of this page
    private long totalPage;                // total page
    private long totalRow;                // total row

    /**
     * Constructor.
     *
     * @param list       the list of paginate result
     * @param pageNumber the page number
     * @param pageSize   the page size
     * @param totalRow   the total row of paginate
     */
    public PageDataBody(List<T> list, int pageNumber, int pageSize, long totalRow) {
        this.list = list;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRow = totalRow;
        this.totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : (totalRow / pageSize) + 1;
    }

    /**
     * Return list of this page.
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Return page number.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Return page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Return total page.
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * Return total row.
     */
    public long getTotalRow() {
        return totalRow;
    }

    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    public boolean isLastPage() {
        return pageNumber == totalPage;
    }
}
