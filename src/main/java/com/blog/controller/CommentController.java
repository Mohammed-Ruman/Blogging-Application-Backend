package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.userservice.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId,
			@PathVariable("userId") Integer userId
			){

		CommentDto createdComment = this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<>(createdComment,HttpStatus.CREATED);

	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){

		this.commentService.deleteComment(commentId);

		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully!!",true), HttpStatus.OK);

	}

	@GetMapping("/comment/{userId}")
	public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable("userId") Integer userId){
		List<CommentDto> commentsByUser = this.commentService.getCommentsByUser(userId);
		return new ResponseEntity<>(commentsByUser,HttpStatus.OK);
	}


}
