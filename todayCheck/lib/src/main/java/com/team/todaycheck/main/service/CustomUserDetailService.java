package com.team.todaycheck.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team.todaycheck.main.entity.UserEntity;
import com.team.todaycheck.main.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired UserRepository loginRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity result = loginRepository.findById(username);
		if(result == null) new UsernameNotFoundException("����ڸ� ã�� �� �����ϴ�.");
		
		return result;
    }

	
}
