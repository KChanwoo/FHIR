package kr.co.connectedin.research.domain.user.dto;

import kr.co.connectedin.research.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequestDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    private String departmentCode;
    private String positionCode;
    private String levelCode;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .loginId(loginId)
                .departmentCode(departmentCode)
                .positionCode(positionCode)
                .levelCode(levelCode)
                .build();
    }
}
