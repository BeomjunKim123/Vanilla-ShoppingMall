package kr.bj.spring.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.bj.spring.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}
