package kr.bj.spring.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import kr.bj.spring.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, 
			QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {


	List<Item> findByItemNm(String ItemNm);
	
	//아이템 이름이나 아이템 상세 설명으로 찾는 쿼리메소드
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	@Query("select i from Item i where i.itemDetail like %:itemDetail% "
			+ "order by i.price asc")
	List<Item> findByItemDetail(@Param("itemDetail")String itemDetail);
	
	
	@Query(value = "select * from item i where i.item_detail like %:itemDetail% "
			+ "order by i.price asc", nativeQuery = true)  
	List<Item> findByItemDetailNative(@Param("itemDetail")String itemDetail);
}