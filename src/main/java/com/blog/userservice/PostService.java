package com.blog.userservice;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostDto getPostById(Integer postId);

	PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	PostResponse getPostsByUser(Integer pageNumber,Integer userId);

	PostResponse getPostsByCategory(Integer pageNumber,Integer categoryId);

	List<PostDto> searchByTitle(String keyword);


}
