package com.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findAllByUser(User user, Pageable p);

	Page<Post> findAllByCategory(Category category, Pageable p);

	@Query("select p from Post p where p.title like :key" )
	List<Post> searchbyTitle(@Param("key") String keyword);

}
