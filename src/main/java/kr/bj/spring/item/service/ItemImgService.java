package kr.bj.spring.item.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import kr.bj.spring.item.entity.ItemImg;
import kr.bj.spring.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;

	//이미지를 저장합니다.
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgfile) throws IOException {
		//파일을 받아온 시점에 보면 multiopart안에 오리지널 이름 등 파일정보가 다 들어있는데 
		//왜냐하면 DB에 넣을때는 원래 이름을 알아야하기 때문이다.
		String oriImgName = itemImgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		//원래 경로가 값이 비어있는지 타임리프 유틸을 이용해서 확인한다.
		if(!StringUtils.isEmpty(oriImgName)) {
			//진짜 이미지 이름 받아옴
			//파일의 정보를 itemImgFile에 다 있으니까 이걸 byte배열로 가져온다.
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgfile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		//실제 상품 이미지 저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		//이미지만 저장 그러니깐 아이템도 저장해줘야함
		itemImgRepository.save(itemImg);
	}
	
	public void updateItemImg(Long ItemImgId, MultipartFile itemImgFile) throws IOException {
		 if(!itemImgFile.isEmpty()) {
			 ItemImg itemImg = itemImgRepository.findById(ItemImgId).orElseThrow(EntityNotFoundException::new);
			 
			 if(!StringUtils.isEmpty(itemImg.getImgName())) {
				 fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
			 }
			 
			 String oriName = itemImgFile.getOriginalFilename();
			 String imgName = fileService.uploadFile(itemImgLocation, oriName, itemImgFile.getBytes());
			 String imgUrl = "/images/item/" + imgName;
			 
			 itemImg.updateItemImg(imgName, imgName, imgUrl);
			
	         
		 }
	}
}
