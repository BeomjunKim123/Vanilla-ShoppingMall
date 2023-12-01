package kr.bj.spring.item.repository;

import static kr.bj.spring.item.entity.QItem.item;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.bj.spring.item.constant.ItemSellStatus;
import kr.bj.spring.item.dto.ItemSearchDto;
import kr.bj.spring.item.entity.Item;
import kr.bj.spring.item.entity.QItem;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

	private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		
		List<Item> content = queryFactory
			.selectFrom(item)
	        .where(regDtsAfter(itemSearchDto.getSearchDateType()),
	                searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
	                searchByLike(itemSearchDto.getSearchBy(),
	                        itemSearchDto.getSearchQuery()))
	        .orderBy(item.id.desc())
	        .offset(pageable.getOffset())
	        .limit(pageable.getPageSize())
	        .fetch();
		
		 long total = queryFactory.select(Wildcard.count).from(item)
	                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
	                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
	                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
	                .fetchOne()
	                ;
 
		 return new PageImpl<>(content, pageable, total);
	}
	
	   private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
	        return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);
	    }

	    private BooleanExpression regDtsAfter(String searchDateType){

	        LocalDateTime dateTime = LocalDateTime.now();

	        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
	            return null;
	        } else if(StringUtils.equals("1d", searchDateType)){
	            dateTime = dateTime.minusDays(1);
	        } else if(StringUtils.equals("1w", searchDateType)){
	            dateTime = dateTime.minusWeeks(1);
	        } else if(StringUtils.equals("1m", searchDateType)){
	            dateTime = dateTime.minusMonths(1);
	        } else if(StringUtils.equals("6m", searchDateType)){
	            dateTime = dateTime.minusMonths(6);
	        }

	        return item.regTime.after(dateTime);
	    }

	    private BooleanExpression searchByLike(String searchBy, String searchQuery){

	        if(StringUtils.equals("itemNm", searchBy)){
	            return item.itemNm.like("%" + searchQuery + "%");
	        } else if(StringUtils.equals("createdBy", searchBy)){
	            return item.createdBy.like("%" + searchQuery + "%");
	        }

	        return null;
	    }

}
