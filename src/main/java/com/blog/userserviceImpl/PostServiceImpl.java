package com.blog.userserviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repository.CategoryRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.userservice.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));

		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));

		Post post = this.mapper.map(postDto, Post.class);

		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post savedpost = this.postRepo.save(post);

		return this.mapper.map(savedpost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post-Id", postId));
		
		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setCategory(category);

		Post updatedpost = this.postRepo.save(post);

		return this.mapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post-Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post-Id", postId));

		return this.mapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByUser(Integer pageNumber,Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		
		org.springframework.data.domain.Pageable p=PageRequest.of(pageNumber, 5,Sort.by("postId").descending());
		
		Page<Post> pagepost = this.postRepo.findAllByUser(user, p);
		List<Post> posts=pagepost.getContent();

		List<PostDto> postDtos = posts.stream().map((post)-> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElements(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastpage(pagepost.isLast());
//		System.out.println(postResponse);
		return postResponse;
		
		
	}

	@Override
	public PostResponse getPostsByCategory(Integer pageNumber,Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));
		
		Sort sort=Sort.by("postId").descending();
		org.springframework.data.domain.Pageable p=PageRequest.of(pageNumber, 5,sort);
		
		 Page<Post> pagepost = this.postRepo.findAllByCategory(cat, p);
		 List<Post> posts=pagepost.getContent();

		List<PostDto> postDtos = posts.stream().map((post)-> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse postResponse=new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElements(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastpage(pagepost.isLast());
//		System.out.println(postResponse);
		return postResponse;
	}

	@Override
	public PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		org.springframework.data.domain.Pageable p= PageRequest.of(pageNumber, pageSize, sort);


		Page<Post> pagepost = this.postRepo.findAll(p);

		List<Post> posts = pagepost.getContent();

		List<PostDto> postDtos = posts.stream().map((post)-> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse postResponse=new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElements(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastpage(pagepost.isLast());

		return postResponse;
	}

	@Override
	public List<PostDto> searchByTitle(String keyword) {
		List<Post> posts = this.postRepo.searchbyTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}


}
