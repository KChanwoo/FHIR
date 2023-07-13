package kr.co.connectedin.research.domain.system.dto;

import kr.co.connectedin.research.domain.system.domain.System;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SystemListResponseDto {

    private String sysCodeId;
    private String domain;
    private String name;
    private String content;
    private String createDate;
    private String editDate;
    private int depth1;
    private int depth2;
    private int depth3;

    public SystemListResponseDto(System entity) {
        this.sysCodeId = entity.getSysCodeId();
        this.domain = entity.getDomain();
        this.name = entity.getName();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.editDate = entity.getEditDate();
        this.depth1 = entity.getDepth1();
        this.depth2 = entity.getDepth2();
        this.depth3 = entity.getDepth3();
    }
}
