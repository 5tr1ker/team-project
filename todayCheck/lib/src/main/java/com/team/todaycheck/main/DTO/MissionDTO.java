package com.team.todaycheck.main.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiParam;

@Getter
@Setter
@NoArgsConstructor
public class MissionDTO {
	@ApiParam(value = "�ĺ���")
    private Long id;
	
	@ApiParam(value = "����Ʈ �����")
    private String postPicture;
	
	@ApiParam(value = "������ �̸�")
    private String adminName;
	
	@ApiParam(value = "������ �ƹ�Ÿ")
    private String adminPicture;

	@ApiParam(value = "�̼� ����Ʈ Ÿ��Ʋ")
    private String postTitle;
	
	@ApiParam(value = "�̼� ����Ʈ ����")
    private String postContent;

	@ApiParam(value = "�̼� ���� ����")
    private LocalDateTime startDate;
	
	@ApiParam(value = "�̼� ���� ����")
    private LocalDateTime endDate;
}
