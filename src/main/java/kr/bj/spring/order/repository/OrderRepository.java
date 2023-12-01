package kr.bj.spring.order.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.bj.spring.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	
}


