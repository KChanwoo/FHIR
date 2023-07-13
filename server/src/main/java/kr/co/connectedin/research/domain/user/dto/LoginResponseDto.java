package kr.co.connectedin.research.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginResponseDto {
    private String name;
    private String departmentName;
    private String positionName;
    private boolean admin;
}
