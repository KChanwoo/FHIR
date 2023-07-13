/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.yonsei.maist.domain.system.dto.SystemResponseDto;
import kr.ac.yonsei.maist.domain.system.exception.InvalidSystemCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;

import static kr.ac.yonsei.maist.domain.system.domain.QSystem.system;

/**
 * The Query of System Code
 */
@RequiredArgsConstructor
@Repository
public class SystemQueryFactory {

    private final JPAQueryFactory queryFactory;

    public static String systemCodeToLike(String code) throws InvalidSystemCodeException {
        if (code == null)
            throw new InvalidSystemCodeException("null");

        if (code.length() != 8) {
            throw new InvalidSystemCodeException(code);
        }

        String[] split = code.split("-");

        if (split.length != 3)
            throw new InvalidSystemCodeException(code);

        StringBuilder like = new StringBuilder();
        for (int i =0;i<split.length;i++) {
            String item = split[i];
            if ("00".equals(item)) {
                like.append("%");
                break;
            } else {
                like.append(item);
                if (i != split.length - 1)
                    like.append("-");
            }
        }

        return like.toString();
    }

    /**
     * check symbol of Integer Candidate
     * @param candidateInteger candidate of integer
     * @return integer value or code (when parameter is not integer)
     */
    private int checkSymbol(String candidateInteger) {
        try {
            return Integer.parseInt(candidateInteger);
        } catch (NumberFormatException e) {
            if (candidateInteger.equals("*"))
                return -1; // find all this level code without this level == 0

            return Integer.MIN_VALUE; // error
        }
    }

    /**
     * get field of depth level
     * @param i depth level - 1
     * @return field of depth
     * @throws Exception field not found exception
     */
    @SuppressWarnings("unchecked")
    private NumberPath<Integer> getField(int i) throws Exception{
        Field field = system.getClass().getField("depth" + (i + 1));
        field.setAccessible(true);
        Object value = field.get(system);

        return (NumberPath<Integer>) value;
    }

    /**
     * get where clause using depth levels
     * @param initWhere init where clause
     * @param depth1 depth1 string value
     * @param depth2 depth2 string value
     * @param depth3 depth3 string value
     * @return where clause
     */
    private BooleanExpression whereClause(BooleanExpression initWhere, String depth1, String depth2, String depth3) {
        try {
            int[] arrDepth = {this.checkSymbol(depth1), this.checkSymbol(depth2), this.checkSymbol(depth3)};

            for (int i = 0; i < arrDepth.length; i++) {
                int currDepth = arrDepth[i];
                if (currDepth >= 0) {
                    initWhere = initWhere.and(this.getField(i).eq(currDepth));
                } else if (i < arrDepth.length - 1) {
                    return initWhere.and(this.getField(i).ne(0)).and(this.getField(i + 1).eq(0));
                } else {
                    return initWhere.and(this.getField(i).ne(0));
                }
            }
        } catch (Exception e) {
            //
        }

        return initWhere;
    }

    /**
     * find all system code by system domain name
     * @param domain domain name
     * @param depth1 depth1 string value
     * @param depth2 depth2 string value
     * @param depth3 depth3 string value
     * @return list of system code
     */
    public List<SystemResponseDto> findAllBySystemDomain(String domain, String depth1, String depth2, String depth3) {
        return queryFactory.select(Projections.fields(SystemResponseDto.class,
                system.sysCodeId.as("id"),
                system.name.as("domain"),
                system.content.as("content"),
                system.domain.as("name"),
                system.depth1.as("depth1"),
                system.depth2.as("depth2"),
                system.depth3.as("depth3")
        ))
        .from(system)
        .where(this.whereClause(system.domain.eq(domain), depth1, depth2, depth3))
        .fetch();
    }

    /**
     * find all domain from system code
     * @return list of system code
     */
    public List<SystemResponseDto> findAllDomain() {
        return queryFactory.select(Projections.fields(SystemResponseDto.class,
                system.sysCodeId.as("id"),
                system.domain.as("domain"),
                system.content.as("content"),
                system.domain.as("name"),
                system.depth1.as("depth1"),
                system.depth2.as("depth2"),
                system.depth3.as("depth3")
        ))
        .from(system)
        .where(system.depth2.eq(0))
        .fetch();
    }
}
