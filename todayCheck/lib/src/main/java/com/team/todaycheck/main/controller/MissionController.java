package com.team.todaycheck.main.controller;

import com.team.todaycheck.main.DTO.MissionDTO;
import com.team.todaycheck.main.DTO.ParticipantDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mission")
public class MissionController {
	
	/**
	 * ���� ��ϵ� ��� �̼��� ��ȸ�Ѵ�
	 * @return ��ϵ� ��� �̼�
	 */
    @GetMapping("")
    @ApiOperation(value = "��� �̼� ��ȸ", notes = "���� ��ϵ� ��� �̼��� ��ȸ�Ѵ�")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "���������� ��ȸ��"),
    		@ApiResponse(code = 500, message = "���� ����"),
	})
    public ResponseEntity<List<MissionDTO>> getAll() {

        List<MissionDTO> list = new ArrayList<>();

        MissionDTO dto = new MissionDTO();
        dto.setId(1L);
        ParticipantDTO admin = new ParticipantDTO();
        admin.setId(1);
        admin.setName("��ö��");
        admin.setAvater("https://firebasestorage.googleapis.com/v0/b/instagram-clone-eb58a.appspot.com/o/default-profile.png?alt=media&token=30f8935d-0920-4ba7-960d-bcf35a0d26aa");
        dto.setAdmin(admin);

        dto.setPostPicture("https://via.placeholder.com/350x150");
        dto.setPostTitle("���� ��ϱ�");
        dto.setPostContent("�δ޵��� �Ϸ絵 �������� �����!");
        
        List<ParticipantDTO> participants = new ArrayList<ParticipantDTO>();
        participants.add(admin);
        ParticipantDTO p = new ParticipantDTO();
        participants.add(p);
        
        p.setId(3);
        p.setName("ȫ�浿");
        p.setAvater("https://firebasestorage.googleapis.com/v0/b/instagram-clone-eb58a.appspot.com/o/default-profile.png?alt=media&token=30f8935d-0920-4ba7-960d-bcf35a0d26aa");
        p.setImage("https://img3.daumcdn.net/thumb/R658x0.q70/?fname=http://t1.daumcdn.net/news/201704/21/KorMedi/20170421131403186ajck.jpg");
        
        p.setId(4);
        p.setName("��ö��");
        p.setAvater("https://firebasestorage.googleapis.com/v0/b/instagram-clone-eb58a.appspot.com/o/default-profile.png?alt=media&token=30f8935d-0920-4ba7-960d-bcf35a0d26aa");
        p.setImage("https://cdn.mindgil.com/news/photo/202009/69795_4370_1604.jpg");

        dto.setParticipants(participants);
        dto.setStartDate(LocalDateTime.of(2022, 11, 1, 0, 0, 0));
        dto.setEndDate(LocalDateTime.of(2022, 12, 31, 0, 0, 0));
        list.add(dto);

        dto = new MissionDTO();
        dto.setId(2L);
        admin = new ParticipantDTO();
        admin.setId(2);
        admin.setName("������");
        admin.setAvater("https://w.namu.la/s/26347546f9950e03e2002698fe3543b59c04f7c36b9f72f3877428419038fcbc82f3a48eb7fc1f51574f2fe2acce2aece3ba7007e08aaa12fca78f3a0125fc5c38999a7a1b5cbcadcaf1ac44e95e1c8a3bdc6f90513ca86d9f2911037289ed48e95cee62fe488a76d02802e71446bef4");
        dto.setAdmin(admin);

        dto.setPostPicture("https://via.placeholder.com/350x150");
        dto.setPostTitle("���� å �б�");
        dto.setPostContent("2�� ���� �Ϸ絵 �������� å �б�!");

        dto.setStartDate(LocalDateTime.of(2022, 11, 1, 0, 0, 0));
        dto.setEndDate(LocalDateTime.of(2022, 11, 15, 0, 0, 0));
        list.add(dto);

        return ResponseEntity.ok().body(list);
    }

    /**
     * �־��� ���̵� ���� �̼��� ��ȯ�Ѵ�
     * 
     * @param id	�˻��� �̼�
     * @return �˻��� �̼�
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "�־��� ���̵� ���� �̼� ��ȸ", notes = "�־��� ���̵� ���� �̼��� ��ȸ�Ѵ�")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "���������� ��ȸ��"),
    		@ApiResponse(code = 400, message = "���̵� ������ �ùٸ��� ����"),
    		@ApiResponse(code = 404, message = "�־��� ���̵� ���� �̼��� �������� ����"),
    		@ApiResponse(code = 500, message = "���� ����"),
	})
    public ResponseEntity<MissionDTO> getMissionById(@ApiParam(value = "����� ���̵�", required = true, example = "1") @PathVariable long id) {
    	if (id != 1) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	MissionDTO dto = new MissionDTO();
        dto.setId(1L);
        ParticipantDTO admin = new ParticipantDTO();
        admin.setId(1);
        admin.setName("��ö��");
        admin.setAvater("https://firebasestorage.googleapis.com/v0/b/instagram-clone-eb58a.appspot.com/o/default-profile.png?alt=media&token=30f8935d-0920-4ba7-960d-bcf35a0d26aa");
        dto.setAdmin(admin);

        dto.setPostPicture("https://via.placeholder.com/350x150");
        dto.setPostTitle("���� ���� ��ϱ�");
        dto.setPostContent("�δ޵��� �Ϸ絵 �������� �����!");
        
        List<ParticipantDTO> participants = new ArrayList<ParticipantDTO>();
        participants.add(admin);
        ParticipantDTO p = new ParticipantDTO();
        participants.add(p);
        
        p.setId(3);
        p.setName("ȫ�浿");
        p.setAvater("https://firebasestorage.googleapis.com/v0/b/instagram-clone-eb58a.appspot.com/o/default-profile.png?alt=media&token=30f8935d-0920-4ba7-960d-bcf35a0d26aa");

        dto.setParticipants(participants);
        dto.setStartDate(LocalDateTime.of(2022, 11, 1, 0, 0, 0));
        dto.setEndDate(LocalDateTime.of(2022, 12, 31, 0, 0, 0));
        
        return ResponseEntity.ok().body(dto);
    }
}
