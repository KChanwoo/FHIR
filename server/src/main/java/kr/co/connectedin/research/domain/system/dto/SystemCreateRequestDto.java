package kr.co.connectedin.research.domain.system.dto;

import kr.co.connectedin.research.domain.system.domain.System;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SystemCreateRequestDto {
    private String sysCodeId;
    @NotEmpty
    private String domain;
    @NotEmpty
    private String name;
    @NotEmpty
    private String content;
    private int depth1;
    private int depth2;
    private int depth3;

    public System toEntity() {
        this.sysCodeId = String.format("%2d-%2d-%2d", depth1, depth2, depth3);
        return System.builder()
                .sysCodeId(sysCodeId)
                .domain(domain)
                .name(name)
                .content(content)
                .depth1(depth1)
                .depth2(depth2)
                .depth3(depth3)
                .build();
    }
}
