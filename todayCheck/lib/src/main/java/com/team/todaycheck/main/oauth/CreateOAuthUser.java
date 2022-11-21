package com.team.todaycheck.main.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.team.todaycheck.main.entity.Token;
import com.team.todaycheck.main.entity.UserEntity;
import com.team.todaycheck.main.entity.UserEntity.Admin;
import com.team.todaycheck.main.repository.UserRepository;
import com.team.todaycheck.main.security.JwtTokenProvider;
import com.team.todaycheck.main.service.JwtService;

@Service
public class CreateOAuthUser {
	
	@Autowired
	UserRepository userRepos;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	JwtService jwtService;

	public Token createNaverUser(String token) {

		String reqURL = "https://kapi.kakao.com/v2/user/me"; // access_token�� �̿��Ͽ� ����� ���� ��ȸ
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + token); // ������ header �ۼ�, access_token����

			// ��� �ڵ尡 200�̶�� ����
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson ���̺귯���� JSON�Ľ�

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			// int id = element.getAsJsonObject().get("id").getAsInt();
			String nickName = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile")
					.getAsJsonObject().get("nickname").getAsString();
			br.close();

			UserEntity user = userRepos.findById(nickName);
			if (user == null) { // �ű� ����
				UserEntity createId = new UserEntity();
				createId.setRoles(Arrays.asList("ROLE_USER"));
				createId.setId(nickName);
				createId.setAdmin(Admin.GENERAL);
				createId.setPassword("3SY2qeoLnho3BqI6jUmPFXTj3ejHEUKz");

				userRepos.save(createId);
				user = createId;
			}

			Token tokenDTO = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
			jwtService.login(tokenDTO);
			return tokenDTO;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Token createGoogleUser(String token) {
		String reqURL = "https://www.googleapis.com/userinfo/v2/me?access_token=" + token;

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + token);

			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			
			while ((line = br.readLine()) != null) {
				result += line;
			}
			
			// Gson ���̺귯���� JSON�Ľ�
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			String nickName = element.getAsJsonObject().get("email").getAsString().split("@")[0];
			System.out.println(nickName);
			
			UserEntity user = userRepos.findById(nickName);
			if (user == null) { // �ű� ����
				UserEntity createId = new UserEntity();
				createId.setRoles(Arrays.asList("ROLE_USER"));
				createId.setId(nickName);
				createId.setAdmin(Admin.GENERAL);
				createId.setPassword("3SY2qeoLnho3BqI6jUmPFXTj3ejHEUKz");
				
				userRepos.save(createId);
				user = createId;
			}
			
			br.close();

			Token tokenDTO = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
			jwtService.login(tokenDTO);
			tokenDTO.setId(nickName);
			tokenDTO.setCode("1");
			return tokenDTO;
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
