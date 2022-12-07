package com.team.todaycheck.main.service;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.todaycheck.main.DTO.MessageDTO;
import com.team.todaycheck.main.DTO.ModifyProfileDTO;
import com.team.todaycheck.main.DTO.ProfileDTO;
import com.team.todaycheck.main.DTO.ProfileMissionDTO;
import com.team.todaycheck.main.entity.UserEntity;
import com.team.todaycheck.main.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService {
	
	@Autowired ProfileRepository profileRepos;
	
	public ProfileDTO getProfile(Long userId) {
		UserEntity user = profileRepos.findById(userId).get();
		List<ProfileMissionDTO> joinMission = profileRepos.getJoinMissionList(userId);
		List<ProfileMissionDTO> createMission = profileRepos.getCreateEntity(userId);
		
		// fetch() ��� ���� null�� �ֱ⶧���� null�� ����
		if(joinMission.get(0).getId() == null) joinMission.remove(0);
		if(createMission.get(0).getId() == null) createMission.remove(0);
		
		return ProfileDTO.builder()
				.userId(user.getUserId())
				.id(user.getId())
				.password(user.getPassword())
				.roles(user.getRoles())
				.address(user.getAddress())
				.phoneNumber(user.getPhoneNumber())
				.joinMission(joinMission)
				.createMission(createMission)
				.build();
	}

	public MessageDTO updateProfile(long parseLong, ModifyProfileDTO profileDTO , String header
			, HttpServletResponse response)	throws AccountNotFoundException {
		
		UserEntity user = profileRepos.findById(parseLong).get();
		String userToken = PostService.getUserIdFromToken(header);
		
		System.out.println(profileDTO.getUserId() + " : " + userToken);
		if(user == null) throw new AccountNotFoundException("������ ã�� �� �����ϴ�.");
		if(profileDTO.getUserId() == null || !userToken.equals(profileDTO.getUserId())) 
			throw new AccountNotFoundException("���� �����ڸ� ������ ������ �� �ֽ��ϴ�.");
		
		if(!profileDTO.getUserId().equals(profileDTO.getId())) { // �� ���� ���̵� ����Ǿ��� ���
			Cookie myCookie = new Cookie("refreshToken", null);
			myCookie.setMaxAge(0);
			myCookie.setPath("/"); // refreshToken ���
			
			profileRepos.setUserFromContent(profileDTO.getUserId() , profileDTO.getId());
		}
		
		user.setId(profileDTO.getId());
		user.setPassword(profileDTO.getPassword());
		user.setAddress(profileDTO.getAddress());
		user.setPhoneNumber(profileDTO.getPhoneNumber());
		
		return MessageDTO.builder()
				.code("1")
				.message("������ ���������߽��ϴ�.")
				.build();
	}
}
