/**
 * Menu domain structure
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.domain.menu.domain;

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
@Table(name = "TBL_MENU")
public class Menu {
    @Id
    @Column(name = "id_menu")
    private String menuId;

    @Column(name = "var_path")
    private String path;

    @Column(name = "var_name")
    private String name;

    @Column(name = "char_is_api")
    private String strIsApi;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;

    @Builder
    public Menu(String menuId, String name, String path, String strIsApi) {
        this.menuId = menuId;
        this.name = name;
        this.path = path;
        this.strIsApi = strIsApi;
    }
}
