package kr.ac.yonsei.maist.domain.user.domain;

import kr.ac.yonsei.maist.domain.user.dto.UserPwdUpdateRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Table(name = "TBL_USER")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int userId;

    @Column(name = "var_loginid")
    private String loginId;

    @Column(name = "enc_password")
    @ColumnTransformer(write = "passwordHash(?)")
    private String password;

    @Column(name = "var_name")
    private String name;

    @Column(name = "sys_department")
    private String departmentCode;

    @Column(name = "sys_position")
    private String positionCode;

    @Column(name = "sys_level")
    private String levelCode;

    @Column(name = "char_is_admin")
    private String adminYn;

    @Column(name = "char_is_used")
    private String useYn;

    @Column(name = "date_create")
    @ColumnTransformer(read = "dateFormat(date_create)")
    private String createDate;

    @Column(name = "date_edit")
    @ColumnTransformer(read = "dateFormat(date_edit)")
    private String editDate;

    public void updatePassword(UserPwdUpdateRequestDto dto) {
        this.password = dto.getPassword();
    }
}
