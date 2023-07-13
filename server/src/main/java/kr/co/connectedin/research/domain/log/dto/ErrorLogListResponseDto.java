package kr.co.connectedin.research.domain.log.dto;

import kr.co.connectedin.research.domain.log.domain.ErrorLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorLogListResponseDto {

    private int logId;
    private String loginId;
    private Integer sysError;
    private String content;
    private String logDate;

    public ErrorLogListResponseDto(ErrorLog entity) {
        this.logId = entity.getLogId();
        this.loginId = entity.getLoginId();
        this.sysError = entity.getErrorCode();
        this.content = entity.getContent();
        this.logDate = entity.getLogDate();
    }
}
