package kr.co.connectedin.research.domain.user.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.connectedin.research.domain.user.domain.User;
import kr.co.connectedin.research.domain.user.dto.UserInformationDto;
import kr.co.connectedin.research.domain.user.dto.UserListResponseDto;
import kr.co.connectedin.research.global.dto.PagingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.ac.yonsei.maist.domain.system.domain.QSystem.system;
import static kr.ac.yonsei.maist.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryFactory {

    private final JPAQueryFactory queryFactory;

    /**
     * Get user information.
     * @param loginId user id
     * @return user information
     */
    public List<UserInformationDto> findUserByLoginId(String loginId) {
        return queryFactory
                .select(Projections.fields(UserInformationDto.class,
                        user.userId.as("userId"),
                        user.loginId.as("loginId"),
                        user.name.as("name"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                .from(system)
                                .where(system.sysCodeId.eq(user.departmentCode)),
                                "departmentName"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.positionCode)),
                                "positionName"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.levelCode)),
                                "levelName")
                ))
                .from(user)
                .where(user.loginId.eq(loginId))
                .fetch();
    }

    /**
     * Find User using id and password.
     * @param loginId user id
     * @param pwd user password
     * @return User instance if exists
     */
    public UserInformationDto findByUserIdAndPwd(String loginId, String pwd) {
        return queryFactory
                .select(Projections.fields(UserInformationDto.class,
                        user.userId.as("userId"),
                        user.loginId.as("loginId"),
                        user.name.as("name"),
                        user.departmentCode.as("departmentCode"),
                        user.positionCode.as("positionCode"),
                        user.levelCode.as("levelCode"),
                        user.adminYn.as("isAdminYN"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.departmentCode)),
                                "departmentName"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.positionCode)),
                                "positionName"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.levelCode)),
                                "levelName")
                ))
                .from(user)
                .where(user.loginId.eq(loginId)
                        .and(user.password.eq(Expressions.stringTemplate("passwordHash({0})", pwd))))
                .fetchOne();
    }

    /**
     * Find User using machine id and user name.
     * @param id machine id
     * @return User instance if exists
     */
    public User findUserById(String id) {
        return queryFactory
                .select(user)
                .from(user)
                .where(user.loginId.eq(id))
                .fetchOne();
    }

    public List<UserListResponseDto> findByLevelCode(PagingDto dto, String levelCode) {
        return queryFactory
                .select(Projections.fields(UserListResponseDto.class,
                        user.loginId.as("loginId"),
                        user.name.as("name"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.departmentCode)),
                                "department"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.positionCode)),
                                "position"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.name)
                                        .from(system)
                                        .where(system.sysCodeId.eq(user.levelCode)),
                                "level")
                ))
                .from(user)
                .where(user.levelCode.eq(levelCode))
                .limit(dto.getElementsPerPage())
                .offset(dto.getCurrentOffset())
                .fetch();
    }
}
