package kr.bj.spring.item.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import kr.bj.spring.item.dto.ItemFormDto;
import kr.bj.spring.item.dto.ItemImgDto;
import kr.bj.spring.item.dto.ItemSearchDto;
import kr.bj.spring.item.entity.Item;
import kr.bj.spring.item.entity.ItemImg;
import kr.bj.spring.item.repository.ItemImgRepository;
import kr.bj.spring.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	private final ItemImgRepository itemImgRepository;
	
	private final ItemImgService itemImgService;
	
	//아이템을 등록했으니깐 아이템 아이디가 넘어왔을 거다
	//itemDto값을 넘겨 받고, multipart 형식으로 되어있는 리스트를 받아온다.
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException  {
		
		//DTO를 entity로 바꾼다. creatItem에서 mapper로 바꿔서
		Item item = itemFormDto.createItem();
		itemRepository.save(item);
		
		//그림 저장하기
		//내가 저장한 이미지의 갯수만큼 돌린다
		for (int i = 0; i < itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			if(i == 0) {
				itemImg.setRepImgYn("Y");
			} else {
				itemImg.setRepImgYn("N");
			}
			
			//실제로 DB에 집어 넣어야한다. 파일 리스트에 있는 애들 중 i번째에 있는 애들을 꺼내서 등록
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		
		//아이디를 반환한다.
		return item.getId();
	}
	
	public ItemFormDto getItemDetail(Long itemId) {
		
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		
		for (ItemImg itemImg : itemImgList) {
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			itemImgDtoList.add(itemImgDto);
		}
		
		Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
		
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		
		return itemFormDto;
	}
	
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {
		
		Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		item.updateItem(itemFormDto);
		
		List<Long> itemImgIds = itemFormDto.getItemImgIds();
		
		for (int i = 0; i < itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
		}
		
		return item.getId();
	}
	
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
		
	}
}
