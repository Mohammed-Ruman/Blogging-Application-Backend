package com.blog.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.blog.entities.LikesCount;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface LikeRepo extends JpaRepository<LikesCount, Integer> {
	
	
//    @Query("Select id from LikesCount "  )
	 public LikesCount findIdByUseridAndPost(int userid,Post post);
	 
	 List<LikesCount> findByUserid(int userid);
}
