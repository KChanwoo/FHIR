package kr.ac.yonsei.maist.domain.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat
public class SystemDownloadResponseDto {

    @JsonProperty
    private Object system;
    @JsonProperty
    private Object language;

    public SystemDownloadResponseDto(Object system, Object language) {
        this.system = system;
        this.language = language;
    }
}
