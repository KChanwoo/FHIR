/**
 * Service for menu
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 * @Date 2022.06.14 - add find all, is allowed to access
 */
package kr.co.connectedin.research.domain.menu.service;

import kr.co.connectedin.research.domain.menu.dao.MenuQueryFactory;
import kr.co.connectedin.research.domain.menu.dao.MenuRepository;
import kr.co.connectedin.research.domain.menu.dto.MenuDto;
import kr.co.connectedin.research.domain.menu.dto.Method;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {

    @NonNull
    private final MenuRepository menuRepository;
    @Autowired
    private MenuQueryFactory menuQueryFactory;

    /**
     * Get menu list that allow to access user
     * @param userLevel User level
     * @return List of menu allow to access user
     */
    @Transactional(readOnly = true)
    public List<MenuDto> findById(String userLevel) throws Exception {
        return menuQueryFactory.findAllByUserLevel(userLevel);
    }

    @Transactional(readOnly = true)
    public List<MenuDto> findAll() throws Exception {
        return menuQueryFactory.findAll();
    }

    @Transactional(readOnly = true)
    public boolean isAllowedToAccess(String loginId, Method method, String path) throws Exception {
        return menuQueryFactory.isAllowedToAccess(loginId, method.name() + ":" + path);
    }
}
