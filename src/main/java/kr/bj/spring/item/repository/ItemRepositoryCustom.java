package kr.bj.spring.item.repository;

import kr.bj.spring.item.dto.ItemSearchDto;
import kr.bj.spring.item.entity.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

   

}
