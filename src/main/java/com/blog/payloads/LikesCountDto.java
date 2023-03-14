package com.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class LikesCountDto {
	
	private String likeUserName;
	
	private int userid;

}
