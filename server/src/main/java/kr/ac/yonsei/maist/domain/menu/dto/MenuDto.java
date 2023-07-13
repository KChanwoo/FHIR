/**
 * DTO for menu item
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.domain.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MenuDto {

    /**
     * menu id
     */
    @NotNull
    private String id;

    /**
     * menu name
     */
    private String name;

    /**
     * menu path
     */
    @NotNull
    private String path;

    /**
     * authorization level
     */
    private String levelCode;
}
