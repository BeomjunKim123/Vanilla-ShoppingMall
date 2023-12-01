package kr.bj.spring.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.bj.spring.member.constant.Role;
import kr.bj.spring.member.dto.MemberFormDto;
import kr.bj.spring.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	private String password;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		member.setRole(Role.USER);
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		return member;
	}
}