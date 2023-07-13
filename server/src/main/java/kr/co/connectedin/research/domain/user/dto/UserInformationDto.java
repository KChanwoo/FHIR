package kr.co.connectedin.research.domain.user.dto;

import kr.co.connectedin.research.domain.menu.dto.MenuDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInformationDto {

    private Integer userId;
    private String loginId;
    private String name;
    private String token;
    private String departmentName;
    private String positionName;
    private String levelName;
    private List<MenuDto> menuList;
    private String isAdminYN;
    private boolean isAdmin;

    private String departmentCode;
    private String positionCode;
    private String levelCode;
}
