package com.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.LikesCount;
import com.blog.payloads.LikesCountDto;
import com.blog.userservice.LikescountService;

@RestController
@RequestMapping("/api/")
public class LikesCountController {
	
	@Autowired
	private LikescountService likescountService;
	
	@PostMapping("/user/{userId}/post/{postId}/like")
	public ResponseEntity<LikesCountDto> createLike(
			@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId)
	{
		LikesCountDto addedLike = this.likescountService.addLike(userId,postId);
		return new ResponseEntity<>(addedLike,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/post/{postId}/like")
	public ResponseEntity<LikesCount> getLike(
			@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId)
	{
		LikesCount gotLike = this.likescountService.searchLike(userId,postId);
		System.out.println(gotLike);
		return new ResponseEntity<>(gotLike,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/like")
	public ResponseEntity<Integer> getLikeCount(@PathVariable("userId") Integer userId){
		Integer likeByUser = this.likescountService.getLikeByUser(userId);
		return new ResponseEntity<>(likeByUser,HttpStatus.OK);
	}

}
