package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
      // retrive post entity by post id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment newComments=commentRepository.save(comment);
        mapToDto(newComments);

        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments =commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        Post post= postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post","id",postId));
        Comment comment =commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("comment","id",commentId));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setEmail(comment.getEmail());
       Comment updatedComment= commentRepository.save(comment);
       return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post= postRepository.findById(postId).orElseThrow(()->new
                ResourceNotFoundException("Post","Id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new
                ResourceNotFoundException("comment","id",commentId));

        commentRepository.delete(comment);
    }

//    @Override
//    public CommentDto getCommentsBYId(long postId, long commentId) {
//        Post post = postRepository.findById(postId).orElseThrow(()->
//                new ResourceNotFoundException("Post","id",postId));
//        Comment comment= commentRepository.findById(commentId).orElseThrow(()->
//                new ResourceNotFoundException("Comment","id",commentId));
//
//
//
//        return null;
//    }


    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto =mapper.map(comment,CommentDto.class);
//CommentDto commentDto = new CommentDto();
//commentDto.setId(comment.getId());
//commentDto.setName(comment.getName());
//commentDto.setEmail(comment.getEmail());
//commentDto.setBody(comment.getBody());

return commentDto;
    }
   private Comment mapToEntity(CommentDto commentDto){
        Comment comment=mapper.map(commentDto,Comment.class);
//Comment comment = new Comment();
//comment.setId(commentDto.getId());
//comment.setName(commentDto.getName());
//comment.setBody(commentDto.getBody());
//comment.setEmail(commentDto.getEmail());
return comment;
   }



}
