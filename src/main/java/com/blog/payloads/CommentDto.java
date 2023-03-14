package com.blog.payloads;

import java.util.Date;

import com.blog.entities.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentDto {

	private int Id;

	private String content;
	
	private String commentUserName;
	
	private Date commentDate;
}
