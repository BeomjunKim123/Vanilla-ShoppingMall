package kr.bj.spring.order.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import kr.bj.spring.item.entity.Item;
import kr.bj.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItem extends BaseEntity {
	
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "order_item_id")
	 private Long id;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "order_id")
	 private Order order;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "item_id")
	 private Item item;
	  
	 private int orderPrice;
	  
	 private int count;
	  
	

}
