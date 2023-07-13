/**
 * domain structure of Menu authorization
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.co.connectedin.research.domain.menu.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TBL_MENU_AUTH")
public class MenuAuth {

    @Id
    @Column(name = "id_menu_auth")
    private int menuAuthId;

    @Column(name = "id_menu")
    private String menuId;

    @Column(name = "sys_level")
    private String levelCode;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;

    @Builder
    public MenuAuth(String menuId, String levelCode) {
        this.menuId = menuId;
        this.levelCode = levelCode;
    }
}
