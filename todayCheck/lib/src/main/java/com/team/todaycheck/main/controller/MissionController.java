package com.team.todaycheck.main.controller;

import com.team.todaycheck.main.DTO.MissionDTO;
import com.team.todaycheck.main.DTO.ParticipantDTO;
import com.team.todaycheck.main.service.IMissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionController {
	
	private final IMissionService missionService;
	
	/**
	 * ���� ��ϵ� ��� �̼��� ��ȸ�Ѵ�
	 * @return ��ϵ� ��� �̼�
	 */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "��� �̼� ��ȸ", notes = "���� ��ϵ� ��� �̼��� ��ȸ�Ѵ�")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "���������� ��ȸ��"),
    		@ApiResponse(code = 500, message = "���� ����"),
	})
    public ResponseEntity<List<MissionDTO>> getAll() {
    	List<MissionDTO> list = missionService.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * �־��� ���̵� ���� �̼��� ��ȯ�Ѵ�
     * 
     * @param id	�˻��� �̼�
     * @return �˻��� �̼�
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "�־��� ���̵� ���� �̼� ��ȸ", notes = "�־��� ���̵� ���� �̼��� ��ȸ�Ѵ�")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "���������� ��ȸ��"),
    		@ApiResponse(code = 400, message = "���̵� ������ �ùٸ��� ����"),
    		@ApiResponse(code = 404, message = "�־��� ���̵� ���� �̼��� �������� ����"),
    		@ApiResponse(code = 500, message = "���� ����"),
	})
    public ResponseEntity<MissionDTO> getMissionById(@ApiParam(value = "����� ���̵�", required = true, example = "1") @PathVariable long id) {
    	MissionDTO mission = missionService.findById(id);
    	if (mission == null) {
    		return ResponseEntity.notFound().build();
    	}
        
        return ResponseEntity.ok().body(mission);
    }
}
