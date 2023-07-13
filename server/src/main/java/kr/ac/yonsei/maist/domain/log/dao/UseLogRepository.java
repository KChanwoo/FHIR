package kr.ac.yonsei.maist.domain.log.dao;

import kr.ac.yonsei.maist.domain.log.domain.UseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface UseLogRepository extends JpaRepository<UseLog, Integer> {

    List<UseLog> findByLoginIdIn(ArrayList loginId);
}
