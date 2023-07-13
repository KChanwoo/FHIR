/**
 * Menu query factory
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 * @Date 2022.06.14 - add find all, is allowed to access
 */
package kr.co.connectedin.research.domain.menu.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.connectedin.research.domain.menu.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.ac.yonsei.maist.domain.menu.domain.QMenu.menu;
import static kr.ac.yonsei.maist.domain.menu.domain.QMenuAuth.menuAuth;
import static kr.ac.yonsei.maist.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class MenuQueryFactory {

    private final JPAQueryFactory queryFactory;

    public List<MenuDto> findAllByUserLevel(String userLevel) {
        return queryFactory
                .select(Projections.fields(MenuDto.class,
                        menu.menuId.as("id"),
                        menu.path.as("path"),
                        menuAuth.levelCode.as("levelCode"),
                        menu.name.as("name")
                ))
                .from(menu)
                .join(menuAuth).on(menu.menuId.eq(menuAuth.menuId))
                .where(menuAuth.levelCode.eq(userLevel))
                .fetch();
    }

    public List<MenuDto> findAll() {
        return queryFactory
                .select(Projections.fields(MenuDto.class,
                        menu.menuId.as("id"),
                        menu.path.as("path"),
                        menuAuth.levelCode.as("levelCode"),
                        menu.name.as("name")
                ))
                .from(menu)
                .join(menuAuth).on(menu.menuId.eq(menuAuth.menuId))
                .fetch();
    }

    public boolean isAllowedToAccess(String loginId, String path) {
        boolean withoutId = queryFactory
                .select(Projections.fields(MenuDto.class,
                        menu.menuId.as("id")
                ))
                .from(menu)
                .join(menuAuth).on(menu.menuId.eq(menuAuth.menuId))
                .where(menuAuth.levelCode.isEmpty().and(menu.path.eq(path)))
                .fetchOne() != null;
        if (loginId == null || "".equals(loginId.trim())) {
            return withoutId;
        } else {
            return withoutId || queryFactory
                    .select(Projections.fields(MenuDto.class,
                            menu.menuId.as("id")
                    ))
                    .from(menu)
                    .join(menuAuth).on(menu.menuId.eq(menuAuth.menuId))
                    .join(user).on(menuAuth.levelCode.eq(user.levelCode))
                    .where(user.loginId.eq(loginId).and(menu.path.eq(path)))
                    .fetchOne() != null;
        }
    }
}
