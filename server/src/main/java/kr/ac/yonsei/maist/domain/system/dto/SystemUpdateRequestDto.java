package kr.ac.yonsei.maist.domain.system.dto;

import kr.ac.yonsei.maist.domain.system.domain.System;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class SystemUpdateRequestDto {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String name;
    @NotEmpty
    private String content;
    private int depth1;
    private int depth2;
    private int depth3;

    public System update(System origin) {
        origin.setDomain(this.domain);
        origin.setName(this.name);
        origin.setContent(this.content);
        origin.setDepth1(this.depth1);
        origin.setDepth2(this.depth2);
        origin.setDepth3(this.depth3);

        return origin;
    }
}
