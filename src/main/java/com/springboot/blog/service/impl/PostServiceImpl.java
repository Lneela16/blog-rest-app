package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper)
    {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert dto into entity
        Post post = mapToEntity(postDto);
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

       Post newPost= postRepository.save(post);

       //convert entity into dto
        PostDto postResponse= mapToDto(newPost);
//        PostDto postResponse= new PostDto();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());



        return postResponse;
    }
//convert entity into dto
    private  PostDto mapToDto(Post post){

        PostDto postDto=modelMapper.map(post,PostDto.class);
//PostDto postDto =new PostDto();
//postDto.setId(post.getId());
//postDto.setDescription(post.getDescription());
//postDto.setTitle(post.getTitle());
//postDto.setContent(post.getContent());

        return postDto;
    }

    //convert dto into entity

    private Post mapToEntity(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
//Post post= new Post();
//post.setId(postDto.getId());
//post.setTitle(postDto.getTitle());
//post.setContent(postDto.getContent());
//post.setDescription(postDto.getDescription());
        return post;
    }
    @Override
    public List<PostDto> getAllPosts() {
      List<Post> posts=  postRepository.findAll();
      List<PostDto> postDtos =posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {

        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));


        return mapToDto(post);
    }

    @Override
    public PostDto UpdatePost(PostDto postDto, long id) {

       Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());

       Post updatedPost=postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {

        Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
    postRepository.delete(post);
    }
}
