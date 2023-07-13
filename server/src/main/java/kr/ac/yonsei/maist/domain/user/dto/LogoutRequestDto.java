package kr.ac.yonsei.maist.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class LogoutRequestDto {

    @NotEmpty
    private String userId;
}
