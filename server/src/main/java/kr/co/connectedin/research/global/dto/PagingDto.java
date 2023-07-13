/**
 * Paging dto
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2020.10.19
 */
package kr.co.connectedin.research.global.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

@Getter
@Setter
public class PagingDto {

    private int currentPageNo = 1; // current page number
    private int pageSize = 10;// number of pages in the page list (setting)
    private int elementsPerPage = 20;// number of posts on one page (setting)
    private long totalRecordCount = 0;// total post
    private long totalPageCount = 0;// total page
    private int firstRecordIndex = 0;// first page index number
    private int lastRecordIndex = 0;// last page index number
    private int firstPageNoOnPageList = 1;// first page number of page list
    private long lastPageNoOnPageList = 1;// last page number in page list
    private HashMap<String, Object> searchSpecificationMap = new HashMap<>();

    public long getTotalPageCount() {
        totalPageCount = ((getTotalRecordCount()-1)/getElementsPerPage()) + 1;
        return totalPageCount;
    }

    public long getCurrentOffset() {
        return (long)elementsPerPage * (currentPageNo - 1);
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo-1, elementsPerPage);
    }
}
