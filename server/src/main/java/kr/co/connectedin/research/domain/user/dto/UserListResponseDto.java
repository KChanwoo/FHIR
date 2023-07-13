package kr.co.connectedin.research.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.connectedin.research.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonFormat
@Getter
@NoArgsConstructor
public class UserListResponseDto {
    private String loginId;
    private String departmentCode;
    private String levelCode;
    private String positionCode;
    private String dateCreate;
    private String dateEdit;
    private String name;
    private String adminYn;
    private String useYn;
    private String department;
    private String position;
    private String level;

    public UserListResponseDto(User user) {
        this.loginId = user.getLoginId();
        this.departmentCode = user.getDepartmentCode();
        this.levelCode = user.getLevelCode();
        this.positionCode = user.getPositionCode();
        this.dateCreate = user.getCreateDate();
        this.dateEdit = user.getEditDate();
        this.name = user.getName();
        this.adminYn = user.getAdminYn();
        this.useYn = user.getUseYn();
    }
}
