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
import com.team.todaycheck.main.exception.DuplicateAccountException;
import com.team.todaycheck.main.repository.ProfileRepository;
import com.team.todaycheck.main.repository.UserRepository;

@Service
@Transactional
public class ProfileService {
	
	@Autowired ProfileRepository profileRepos;
	@Autowired UserRepository userRepos;
	
	public ProfileDTO getProfile(String accoundId) throws AccountNotFoundException {
		UserEntity user = profileRepos.findById(accoundId);
		if(user == null) throw new AccountNotFoundException("�������� �ʴ� ȸ�� �����Դϴ�.");
		List<ProfileMissionDTO> joinMission = profileRepos.getJoinMissionList(accoundId);
		List<ProfileMissionDTO> createMission = profileRepos.getCreateEntity(accoundId);
		
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

	public MessageDTO updateProfile(String accoundId , ModifyProfileDTO profileDTO , String header
			, HttpServletResponse response)	throws AccountNotFoundException {
		
		UserEntity user = profileRepos.findById(accoundId);
		String userToken = PostService.getUserIdFromToken(header);
		
		if(user == null) throw new AccountNotFoundException("������ ã�� �� �����ϴ�.");
		if(profileDTO.getUserId() == null || !userToken.equals(profileDTO.getUserId())) 
			throw new AccountNotFoundException("���� �����ڸ� ������ ������ �� �ֽ��ϴ�.");
		
		if(!profileDTO.getUserId().equals(profileDTO.getId())) { // �� ���� ���̵� ����Ǿ��� ���
			UserEntity result = userRepos.findById(profileDTO.getId());
			if(result != null) throw new DuplicateAccountException("�̹� �����ϴ� ȸ���Դϴ�.");
			
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
