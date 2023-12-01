package kr.bj.spring.order.repository;

import kr.bj.spring.order.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
