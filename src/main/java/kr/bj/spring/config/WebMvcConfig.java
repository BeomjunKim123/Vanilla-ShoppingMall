package kr.bj.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
	
	
	@Value(value = "${uploadPath}")
	private String uploadPath;

	@Override
	//registry 를 등록해서 쓸 수 있게함
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**", "/templates/**") //접근을 이 주소로 하게 됩니다.
			.addResourceLocations(uploadPath, "classpath:/resources/"); //실제로는 여기입니다.
	}
	
	

}
