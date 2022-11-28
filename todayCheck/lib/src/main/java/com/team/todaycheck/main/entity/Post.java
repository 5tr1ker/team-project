package com.team.todaycheck.main.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postKey;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false , length=50)
	private String userId;
	
	@Column(nullable = false , length=200)
	private String title;
	
	@Column(nullable = false , length=1000)
	private String description;
	
	@Column(nullable = false , length=100)
	private String thumbnail;
	
	@Column(nullable = false)
	private Integer views;
	
	@Column(nullable = false)
	private Integer recommendation;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId_id", nullable = false)
	private UserEntity userEntity;
	
	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "post" , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY , orphanRemoval = true)
	private List<Recommander> recommander = new LinkedList<Recommander>();
	
	@Builder.Default
	@OneToMany(mappedBy = "post" , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY , orphanRemoval = true)
	private List<Comment> comment = new LinkedList<Comment>();
	
	/**
     * insert �Ǳ��� (persist �Ǳ���) ����ȴ�.
     * */
	@PrePersist
	public void prePersist() {
		this.views = this.recommendation == null ? 0 : this.views;
	}
	
	// �������� ���� �޼ҵ�
	public void addRecommander(Recommander rc) {
		recommander.add(rc);
		rc.setPost(this);
	}
	
	public void addComment(Comment comment) {
		this.comment.add(comment);
		comment.setPost(this);
	}
}
