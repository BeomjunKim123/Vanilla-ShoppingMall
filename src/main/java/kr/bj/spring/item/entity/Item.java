package kr.bj.spring.item.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import kr.bj.spring.item.constant.ItemSellStatus;
import kr.bj.spring.item.dto.ItemFormDto;
import kr.bj.spring.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;			// 상품 코드 
	
	@Column(nullable = false, length = 50)
	private String itemNm;		// 상품 이름
	
	@Column(nullable = false)
	private int price;			// 상품 가격
	
	@Column(nullable = false, name = "number")
	private int stockNumber;	// 재고 수량
	
	
	//열거형은 기본적으로 숫자로 다루는 것이 원칙. 해당 어노테이션은 열거형을 나타내는 어노테이션
	//ordianl = 숫자로 다룬다. string = 문자로 다룬다.
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;
	
	
	@Lob
	@Column(nullable = false)
	private String itemDetail;	// 상품 상세 설명 
	
//	@CreationTimestamp
//	private LocalDateTime regTime;		// 등록 시간	
//	
//	@UpdateTimestamp
//	private LocalDateTime updateTime;	// 수정 시간
	
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
		
	}
}
