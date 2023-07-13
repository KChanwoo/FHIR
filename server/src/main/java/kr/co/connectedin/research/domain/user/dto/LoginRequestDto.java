package kr.co.connectedin.research.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
}
