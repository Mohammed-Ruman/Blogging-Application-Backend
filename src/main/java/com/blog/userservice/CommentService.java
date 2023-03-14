package com.blog.userservice;

import java.util.List;

import com.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId, Integer userId);

	void deleteComment(Integer commentId);

	List<CommentDto> getCommentsByUser(Integer userId);
}
