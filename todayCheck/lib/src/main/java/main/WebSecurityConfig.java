package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import main.security.JwtAuthenticationFilter;
import main.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	// https://taesan94.tistory.com/109
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()	// Post ��û block ����
        .authorizeRequests() // �ش� �޼ҵ� �Ʒ��� �� ��ο� ���� ������ ������ �� �ִ�.
        	.antMatchers("/admin/**").authenticated() // ������ �ǽ�
            .antMatchers("/admin/**").hasRole("ADMIN") // ��ȣ�� ������ ���� ������ ���ٰ���, ROLE_�� �پ ���� ��. ��, ���̺� ROLE_���Ѹ� ���� �����ؾ� ��.
            .antMatchers("/user/**").authenticated() // ������ �ǽ�
            .antMatchers("/user/**").hasRole("USER") 
            .antMatchers("/**").permitAll() // �̿� ��û�� ������ ����
            .anyRequest().authenticated()  //  �α��ε� ����ڰ� ��û�� ������ �� �ʿ��ϴ�  ���� ����ڰ� �������� �ʾҴٸ�, ������ ��ť��Ƽ ���ʹ� ��û�� ��Ƴ��� ����ڸ� �α��� �������� �����̷��� ���ش�.
            .and()
         .logout()
             .permitAll()
             .logoutUrl("/logout") // �α׾ƿ� url
             //.logoutSuccessUrl("/")
             .and()
         .exceptionHandling()
			.accessDeniedPage("/accessDenied_page"); // ������ ���� ����� �������õ����� ��
		
		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), // ����
				UsernamePasswordAuthenticationFilter.class);
	}
}
