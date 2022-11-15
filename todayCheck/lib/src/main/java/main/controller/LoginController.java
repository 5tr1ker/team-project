package main.controller;

import java.util.Map;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.DTO.LoginRequestDTO;
import main.DTO.LoginResponseDTO;
import main.DTO.MessageDTO;
import main.DTO.RegistryDTO;
import main.service.JwtService;
import main.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDto , HttpServletResponse response) throws AccountNotFoundException {
		LoginResponseDTO result = loginService.login(loginDto);
		Cookie cookie = new Cookie("refreshToken", result.getRefreshToken());
		cookie.setPath("/");
		cookie.setMaxAge(1209600);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return result;
	}
	/* */
	@RequestMapping(value = "/admin/test" , method = RequestMethod.GET)
	public String testAdmin() {
		return "���� ������";
	}
	
	@RequestMapping(value = "/user/test" , method = RequestMethod.GET)
	public String testUser() {
		return "���� ������ POST";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public MessageDTO register(@RequestBody RegistryDTO data) throws AccountException {
		return loginService.register(data);
	}

	@RequestMapping(value = "/accessDenied_page", method = RequestMethod.GET)
	public MessageDTO accessDenied() {
		return MessageDTO.builder()
				.code("-1")
				.message("�ΰ����� ���� ��û")
				.build();
	}
	
	@RequestMapping(value = "/refresh" , method = RequestMethod.GET) // 
	public MessageDTO validateRefreshToken(@CookieValue(name = "refreshToken") String cookie) {
		Map<String, String> map = jwtService.validateRefreshToken(cookie);
		if(map.get("code").equals("-1")) {
			return MessageDTO.builder()
					.code("-1")
					.message(HttpStatus.UNAUTHORIZED.toString())
					.build();
		}
		
		return MessageDTO.builder()
				.code("-1")
				.message(HttpStatus.OK.toString())
				.build();
	}
}
