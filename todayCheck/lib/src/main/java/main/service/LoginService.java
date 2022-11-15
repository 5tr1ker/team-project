package main.service;

import java.util.Collections;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.DTO.LoginRequestDTO;
import main.DTO.LoginResponseDTO;
import main.DTO.MessageDTO;
import main.DTO.RegistryDTO;
import main.entity.Token;
import main.entity.UserEntity;
import main.entity.UserEntity.Admin;
import main.repository.UserRepository;
import main.security.JwtTokenProvider;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private JwtService jwtService;
	
	public LoginResponseDTO login(LoginRequestDTO data) throws AccountNotFoundException {
		UserEntity result = userRepos.findById(data.getId());
		if (result == null)
			throw new AccountNotFoundException("�������� �ʴ� ���̵��Դϴ�.");
		if (!result.getPassword().equals(data.getPassword())) {
			throw new AccountNotFoundException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
		
		Token tokenDTO = jwtTokenProvider.createAccessToken(result.getUsername(), result.getRoles());
		jwtService.login(tokenDTO);
		
		return LoginResponseDTO.builder()
				.id(result.getId())
				.password(result.getPassword())
				.code("1")
				.AccessToken(tokenDTO.getAccessToken())
				.RefreshToken(tokenDTO.getRefreshToken())
				.grantType(tokenDTO.getGrantType())
				.build();
	}
	
	public MessageDTO register(RegistryDTO data) throws AccountException {
		try {
			UserEntity findId = userRepos.findById(data.getId());
			if (findId != null)
				throw new AccountException("�̹� �����ϴ� ���̵��Դϴ�.");
			UserEntity result = UserEntity.builder()
					.id(data.getId())
					.password(data.getPassword())
					.roles(Collections.singletonList("ROLE_USER"))
					.admin(Admin.GENERAL)
					.build();

			userRepos.save(result);
		} catch(AccountException e) {
			throw new AccountException(e.getMessage());
		} catch (Exception e) {
			return MessageDTO.builder()
					.code("-1")
					.message("������ ������ �߻��߽��ϴ�. ����� �ٽ� �̿����ּ���.")
					.build();
		}
		return MessageDTO.builder()
				.code("1")
				.message("���������� ȸ�������� �Ǿ����ϴ�.")
				.build();
	}
}
