package com.blog.userserviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.LikesCount;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.LikesCountDto;
import com.blog.repository.LikeRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.userservice.LikescountService;
import com.blog.userservice.UserService;

@Service
public class LikescounterServiceImpl implements LikescountService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private LikeRepo likeRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PostRepo postRepo;
	
	


	@Override
	public LikesCount searchLike(Integer userId, Integer postId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

		LikesCount foundlike = this.likeRepo.findIdByUseridAndPost(user.getId(), post);
		return foundlike;
	}
	
	
	@Override
	public LikesCountDto addLike(Integer userId,Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		LikesCount searchedLike = searchLike(userId, postId);
		
		
		if(searchedLike!=null ) {
			return null;
		}
		
		else {
		LikesCount count=new LikesCount();
		count.setPost(post);
		count.setLikeUsername(user.getName());
		count.setUserid(user.getId());
		
		LikesCount savedCount=this.likeRepo.save(count);
		
		return this.mapper.map(savedCount,LikesCountDto.class);
		}
	}


	@Override
	public Integer getLikeByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<LikesCount> findByUserid = this.likeRepo.findByUserid(user.getId());
		return findByUserid.size();
	}


	


	
	
	
}
