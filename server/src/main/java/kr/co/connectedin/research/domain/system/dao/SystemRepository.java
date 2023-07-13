/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.dao;

import kr.ac.yonsei.maist.domain.system.domain.System;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface SystemRepository extends PagingAndSortingRepository<System, Integer> {

    int countByEditDateAfter(String downloadDate);

    List<System> findByEditDateAfter(String downloadDate);

    Optional<System> findBySysCodeId(String sysCodeId);

    List<System> findByDomain(String domain);
}
