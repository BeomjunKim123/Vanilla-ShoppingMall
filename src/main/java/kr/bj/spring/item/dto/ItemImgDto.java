package kr.bj.spring.item.dto;

import org.modelmapper.ModelMapper;

import kr.bj.spring.item.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString()
public class ItemImgDto {
	
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repImgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ItemImgDto of(ItemImg itemImg) {
		//아이템 이미지 DTO로 받아서 매핑
		return modelMapper.map(itemImg, ItemImgDto.class);
	}

}
