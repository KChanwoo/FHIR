package kr.ac.yonsei.maist.domain.log.dao;

import kr.ac.yonsei.maist.domain.log.domain.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Integer> {

    List<ErrorLog> findByLoginIdIn(ArrayList userId);
}
