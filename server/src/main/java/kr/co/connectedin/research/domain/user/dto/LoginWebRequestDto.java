package kr.ac.yonsei.maist.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LoginWebRequestDto {

    @NotNull
    private int userId;
    @NotEmpty
    private String password;
    @NotNull
    private int language;
}
