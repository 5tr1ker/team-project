package com.team.todaycheck.main.DTO;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileMissionDTO {
	@ApiParam(value = "�ĺ���")
    private Long id;

	@ApiParam(value = "�̼� ����Ʈ Ÿ��Ʋ")
    private String title;
	
	@ApiParam(value = "����Ʈ �����")
    private String thumbnail;
	
	@ApiParam(value = "�̼� ����Ʈ ����")
    private String content;

	@ApiParam(value = "�̼� ���� ����")
    private LocalDateTime startDate;
	
	@ApiParam(value = "�̼� ���� ����")
    private LocalDateTime endDate;
}
