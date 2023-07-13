/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.dto;

import kr.ac.yonsei.maist.domain.system.domain.System;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SystemResponseDto {
    String id;
    String domain;
    String name;
    String content;
    int depth1;
    int depth2;
    int depth3;

    public SystemResponseDto(System system) {
        this.id = system.getSysCodeId();
        this.domain = system.getDomain();
        this.name = system.getName();
        this.content = system.getContent();
        this.depth1 = system.getDepth1();
        this.depth2 = system.getDepth2();
        this.depth3 = system.getDepth3();
    }
}
