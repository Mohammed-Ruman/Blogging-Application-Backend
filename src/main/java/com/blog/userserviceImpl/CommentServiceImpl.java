package com.blog.userserviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repository.CommentRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.userservice.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper mapper;



	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {

		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postID:", postId));

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

		Comment comment = this.mapper.map(commentDto, Comment.class);

		comment.setPost(post);
		comment.setUser(user);
		comment.setCommentUserName(user.getName());
		comment.setCommentDate(new Date());

		Comment savedcomment = this.commentRepo.save(comment);

		return this.mapper.map(savedcomment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));

		this.commentRepo.delete(com);

	}

	@Override
	public List<CommentDto> getCommentsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

		List<Comment> comments = this.commentRepo.findByUser(user);

		List<CommentDto> commentDto = comments.stream().map(comment -> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());

		return commentDto;
	}



}
