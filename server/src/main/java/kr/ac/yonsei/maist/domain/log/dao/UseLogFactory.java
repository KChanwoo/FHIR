package kr.ac.yonsei.maist.domain.log.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.yonsei.maist.domain.log.dto.UseLogListResponseDto;
import kr.ac.yonsei.maist.domain.log.dto.UseLogRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.ac.yonsei.maist.domain.log.domain.QUseLog.useLog;
import static kr.ac.yonsei.maist.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UseLogFactory {

    private final JPAQueryFactory queryFactory;

    private NumberTemplate<Integer> weekOfYear() {
        return Expressions.numberTemplate(Integer.class, "DATE_FORMAT({0}, '%v') - DATE_FORMAT(STR_TO_DATE( CONCAT(DATE_FORMAT({0}, '%Y'), DATE_FORMAT({0}, '%m'), '01'), '%Y%m%d'), '%v') + 1", useLog.logDate);
    }

    public List<UseLogListResponseDto> findAllLogData(UseLogRequestDto dto) {

        return queryFactory
                .select(Projections.fields(UseLogListResponseDto.class,
                        useLog.logId.as("logId"),
                        useLog.loginId.as("loginId"),
                        useLog.logDate.as("logDate"),
                        useLog.content.as("content"),
                        useLog.menuId.as("sysMenu"),
                        useLog.ip.as("ip")
                ))
                .from(useLog)
                .join(user).on(useLog.loginId.eq(user.loginId))
                .where(user.loginId.eq(dto.getLoginId()))
                .fetch();
    }

}
