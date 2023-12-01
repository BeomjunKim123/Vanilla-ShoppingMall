package kr.bj.spring.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.bj.spring.item.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{

	
	List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
