package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.userservice.FileService;
import com.blog.userservice.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody  PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){

		PostDto createdpost = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<>(createdpost, HttpStatus.CREATED);

	}

	@GetMapping("user/{userId}/post")
	public ResponseEntity<PostResponse> getPostsByUser(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@PathVariable Integer userId){

		PostResponse postsByUser = this.postService.getPostsByUser(pageNumber, userId);

		return new ResponseEntity<>(postsByUser, HttpStatus.OK);
	}

	@GetMapping("category/{categoryId}/post")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@PathVariable Integer categoryId){

		PostResponse postsByCategory = this.postService.getPostsByCategory(pageNumber,categoryId);

		return new ResponseEntity<>(postsByCategory,HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postById = this.postService.getPostById(postId);
		return new ResponseEntity<>(postById, HttpStatus.OK);
	}

	@GetMapping("post")
	public ResponseEntity<PostResponse> getPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
	  PostResponse posts = this.postService.getPosts(pageNumber,pageSize, sortBy, sortDir);

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post deleted successsfully", true), HttpStatus.OK);
	}

	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost,HttpStatus.OK);
	}

	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> search(@PathVariable("keyword") String keyword  ){
		List<PostDto> searchpost = this.postService.searchByTitle(keyword);
		return new ResponseEntity<>(searchpost,HttpStatus.OK);
	}

	@PostMapping("/post/image/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId
			) throws IOException{

		PostDto postDto = this.postService.getPostById(postId);

		String filename = this.fileService.uploadimage(path, image);

		postDto.setImageName(filename);

		PostDto updatePost = this.postService.updatePost(postDto, postId);

		return new ResponseEntity<>(updatePost,HttpStatus.OK);
	}

	@GetMapping(value="/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String image,
			HttpServletResponse response
			) throws IOException {

		InputStream img = this.fileService.getImage(path, image);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(img, response.getOutputStream());

	}
}
