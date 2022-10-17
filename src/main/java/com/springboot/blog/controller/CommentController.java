package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,
                                                     @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(name = "postId") long postId){

        return commentService.getCommentsByPostId(postId);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId,@PathVariable(name = "commentId") Long commentId,
                                                @RequestBody CommentDto commentDto){
CommentDto updatedComment=commentService.updateComment(postId, commentId, commentDto);
    return new ResponseEntity<>(updatedComment,HttpStatus.OK);
}

@DeleteMapping("/posts/{postId}/comments/{commentId}")
public String deleteComment(@PathVariable(name = "postId") long postId,
                            @PathVariable(name = "commentId") long commentId){

        commentService.deleteComment(postId,commentId);
    return "deleted comment Successfully";
}
}
