package com.blog.userservice;

import java.util.ArrayList;
import java.util.List;

import com.blog.entities.LikesCount;
import com.blog.entities.User;
import com.blog.payloads.LikesCountDto;

public interface LikescountService {
	
	LikesCountDto addLike(Integer userId, Integer postId);
	
	LikesCount searchLike(Integer userId, Integer postId);
	
	Integer getLikeByUser(Integer userId);

}
