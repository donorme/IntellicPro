package com.intellicpro.patent.auth.repository;


import com.intellicpro.patent.auth.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member save(Member member);

    Optional<Member> findByCode(String code);

    boolean existsByCode(String code);
}
