package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;
import com.blog.entities.User;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	List<Comment> findByUser(User user);
}
