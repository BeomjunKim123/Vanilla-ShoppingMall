package kr.bj.spring.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.bj.spring.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email);
}
