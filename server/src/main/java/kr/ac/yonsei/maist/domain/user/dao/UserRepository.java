package kr.ac.yonsei.maist.domain.user.dao;

import kr.ac.yonsei.maist.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByLoginId(String loginId);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    long count(Specification<User> spec);

    void deleteByLoginId(String loginId);
}
