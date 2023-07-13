/**
 * Service that performs all logic such as inquiry, creation, modification, deletion of user
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.co.connectedin.research.domain.user.service;

import kr.co.connectedin.research.domain.system.dao.SystemQueryFactory;
import kr.co.connectedin.research.domain.system.exception.InvalidSystemCodeException;
import kr.co.connectedin.research.domain.user.constant.UserConstant;
import kr.co.connectedin.research.domain.user.dao.UserQueryFactory;
import kr.co.connectedin.research.domain.user.dao.UserRepository;
import kr.co.connectedin.research.domain.user.domain.User;
import kr.ac.yonsei.maist.domain.user.dto.*;
import kr.co.connectedin.research.domain.user.exception.IdAndPasswordNotMatched;
import kr.co.connectedin.research.global.dto.PagingDto;
import kr.co.connectedin.research.domain.user.dto.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    @NonNull
    private final UserRepository userRepository;
    @Autowired
    private UserQueryFactory userQueryFactory;

    public Specification<User> searchWith(Map<String, Object> searchMap) throws InvalidSystemCodeException {
        return new Specification<User>() {
            @SneakyThrows
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<Predicate>();

                if (searchMap.containsKey("department") && !"".equals(searchMap.get("department")))
                    predicate.add(criteriaBuilder.like(root.get("departmentCode").as(String.class),
                            SystemQueryFactory.systemCodeToLike(searchMap.get("department").toString())));

                if (searchMap.containsKey("level") && !"".equals(searchMap.get("level")))
                    predicate.add(criteriaBuilder.like(root.get("levelCode").as(String.class),
                            SystemQueryFactory.systemCodeToLike(searchMap.get("level").toString())));

                if (searchMap.containsKey("keyword") && !"".equals(searchMap.get("keyword")))
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + searchMap.get("keyword") + "%"));

                return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
            }
        };
    }

    /**
     * Create a user.
     * @param dto user information
     */
    @Transactional
    public User createUser(UserCreateRequestDto dto) throws Exception {
        User user = userRepository.save(dto.toEntity());
        if (UserConstant.ADMIN_CODE.equals(user.getLevelCode())) {
            user.setAdminYn("Y");
        } // default of adminYN is 'N' (defined in the database)

        return user;
    }

    /**
     * Update user information.
     * @param id machine id
     * @param dto user information
     */
    @Transactional
    public void updateUser(String id, UserUpdateRequestDto dto) throws Exception {
        User entity = userQueryFactory.findUserById(id);
        if(entity == null) {
            throw new IllegalArgumentException("id="+id);
        }

        if (UserConstant.ADMIN_CODE.equals(entity.getLevelCode())) {
            entity.setAdminYn("Y");
        } else {
            entity.setAdminYn("N");
        }

        entity = dto.update(entity);
        userRepository.save(entity);
    }

    /**
     * Get user information.
     * @param loginId user id
     * @return user information
     */
    @Transactional
    public User findByLoginId(String loginId) throws Exception {
        User entity = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("id="+loginId));
        return entity;
    }

    /**
     * Get user information joined with the system code.
     * @param loginId user id
     * @return user information
     */
    @Transactional(readOnly = true)
    public List<UserInformationDto> findUserByLoginId(String loginId) throws Exception {
        List<UserInformationDto> user = userQueryFactory.findUserByLoginId(loginId);

        return user;
    }

    /**
     * Find user using user id and password.
     * @param loginId user login id
     * @param pwd user password
     * @return User instance if exists
     */
    @Transactional(readOnly = true)
    public UserInformationDto findByIdAndPassword(String loginId, String pwd) throws Exception {
        UserInformationDto entity = userQueryFactory.findByUserIdAndPwd(loginId, pwd);
        if(entity == null) {
            throw new IdAndPasswordNotMatched();
        }
        return entity;
    }

    /**
     * Check whether the user ID exists.
     * @param loginId User Id
     * @return UserExistResponseDto
     */
    @Transactional(readOnly = true)
    public UserExistResponseDto userExist(String loginId) {
        User entity = userRepository.findByLoginId(loginId)
                .orElseGet(User::new);

        UserExistResponseDto dto = new UserExistResponseDto(false);
        if(entity.getLoginId() != null) {
            dto.setIsExisted(true);
        }
        return dto;
    }

    /**
     * Update user password.
     * @param loginId User Id
     * @param dto UserPwdUpdateRequestDto
     */
    @Transactional
    public void update(String loginId, UserPwdUpdateRequestDto dto) throws Exception {
        User entity = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("id="+loginId));

        entity.updatePassword(dto);
        this.userRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public long countUser(FindUserRequestDto dto) throws Exception {
        return userRepository.count(this.searchWith(dto.getSearchSpecificationMap()));
    }

    @Transactional(readOnly = true)
    public List<UserListResponseDto> findUser(FindUserRequestDto dto) throws Exception {

        Pageable paging = dto.toPageable();
        Page<User> userPage = null;
        if (dto.getSearchSpecificationMap().keySet().size() > 0)
            userPage = userRepository.findAll(this.searchWith(dto.getSearchSpecificationMap()), paging);
        else
            userPage = userRepository.findAll(paging);

        return userPage
                .stream()
                .map(UserListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUserByLoginId(String loginId) {
        userRepository.deleteByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public long countResearcher() throws Exception {
        HashMap<String, Object> searchMap = new HashMap<>();
        searchMap.put("level", UserConstant.RESEARCHER_CODE);
        return userRepository.count(this.searchWith(searchMap));
    }

    @Transactional(readOnly = true)
    public List<UserListResponseDto> findResearcher(PagingDto dto) throws Exception {
        return userQueryFactory.findByLevelCode(dto, UserConstant.RESEARCHER_CODE);
    }
}
