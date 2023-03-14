package com.blog.entities;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comments")
@Data
@Getter
@Setter

public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;

	private String content;
	
	private String commentUserName;
	
	private Date commentDate;

	@ManyToOne
	private Post post;

	@ManyToOne
	private User user;
}
