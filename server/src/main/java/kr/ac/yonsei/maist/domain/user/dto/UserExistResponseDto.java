package kr.ac.yonsei.maist.domain.user.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserExistResponseDto {

    private Boolean isExisted;

    public UserExistResponseDto(Boolean exist) {
        this.isExisted = exist;
    }

    public Boolean getIsExisted() {
        return isExisted;
    }

    public void setIsExisted(Boolean exist) {
        isExisted = exist;
    }
}
