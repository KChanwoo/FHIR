package kr.ac.yonsei.maist.global.response.dataMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingDataMessage {

    private String id = "200";
    private String version = "0.1" ;
    private String result = "success";
    private long totalPages = 1;
    private long totalElements = 0;
    private int elementsPerPage;
    private Object data;

    public PagingDataMessage() {}

    @Builder
    public PagingDataMessage(long totalPages, long totalElements, int elementsPerPage, Object data) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.elementsPerPage = elementsPerPage;
        this.data = data;
    }
}
