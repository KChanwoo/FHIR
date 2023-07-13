package kr.ac.yonsei.maist.domain.user.dto;

import javax.validation.constraints.NotEmpty;

public class UserPwdUpdateRequestDto {

    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }
}
