package kr.ac.yonsei.maist.domain.log.dto;

import kr.ac.yonsei.maist.domain.log.domain.UseLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UseLogListResponseDto {

    private int logId;
    private String loginId;
    private String menuId;
    private String content;
    private String logDate;
    private String ip;

    public UseLogListResponseDto(UseLog entity) {
        this.logId = entity.getLogId();
        this.loginId = entity.getLoginId();
        this.menuId = entity.getMenuId();
        this.content = entity.getContent();
        this.logDate = entity.getLogDate();
        this.ip = entity.getIp();
    }
}
