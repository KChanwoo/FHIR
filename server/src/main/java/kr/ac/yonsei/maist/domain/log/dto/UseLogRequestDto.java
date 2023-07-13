package kr.ac.yonsei.maist.domain.log.dto;

import kr.ac.yonsei.maist.domain.log.domain.UseLog;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UseLogRequestDto {

    @NotEmpty
    private String loginId;
    private String menuId;
    private String content;
    private String ip;

    public UseLog toEntity() {
        return UseLog.builder()
                .loginId(loginId)
                .menuId(menuId)
                .content(content)
                .ip(ip)
                .build();
    }
}
