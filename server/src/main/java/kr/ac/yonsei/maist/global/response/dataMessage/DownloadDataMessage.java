package kr.ac.yonsei.maist.global.response.dataMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadDataMessage {

    private String id = "200";
    private String version = "0.1" ;
    private String result = "success";
    private String downloadDate;
    private int totalElements;
    private Object data;

    public DownloadDataMessage() {}

    @Builder
    public DownloadDataMessage(String downloadDate, int totalElements, Object data) {
        this.downloadDate = downloadDate;
        this.totalElements = totalElements;
        this.data = data;
    }
}
