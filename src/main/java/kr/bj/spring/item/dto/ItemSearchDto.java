package kr.bj.spring.item.dto;

import groovy.transform.ToString;
import kr.bj.spring.item.constant.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ToString
public class ItemSearchDto {

    private String searchDateType; //날짜형태

    private ItemSellStatus searchSellStatus;

    private String searchBy; //누구의 의해서 방법찾기

    private String searchQuery = ""; //들어가야할 검색어, 키워드

}
