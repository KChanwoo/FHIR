package kr.co.connectedin.research.domain.user.dto;

import kr.co.connectedin.research.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String password;
    private String departmentCode;
    private String positionCode;
    private String levelCode;
    private String adminYn;
    private String useYn;

    public User update(User origin) {
        origin.setName(this.name);
        origin.setAdminYn(this.adminYn);
        origin.setDepartmentCode(this.departmentCode);
        origin.setPositionCode(this.positionCode);
        if (this.password != null && this.password.length() > 0)
            origin.setPassword(this.password);
        origin.setLevelCode(this.levelCode);
        origin.setUseYn(this.useYn);
        return origin;
    }
}
