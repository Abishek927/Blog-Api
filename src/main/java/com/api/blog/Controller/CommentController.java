package com.api.blog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.Payloads.ApiResponse;
import com.api.blog.Payloads.CommentDTO;
import com.api.blog.Service.CommentService;

@RestController
@RequestMapping(value = "/")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping("post/{postId}/comment")
    public ResponseEntity<CommentDTO> createCommentController(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDTO commentDTO

    ) {


        CommentDTO createComment = this.commentService.createComment(postId, commentDTO);
        return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);


    }

    @GetMapping("post/{postId}/comment")
    public ResponseEntity<List<CommentDTO>> getAllCommentByPostController(
            @PathVariable("postId") Long postId
    ) {
        List<CommentDTO> allCommentByPost = this.commentService.getAllCommentByPost(postId);

        return new ResponseEntity<>(allCommentByPost, HttpStatus.OK);

    }

    @GetMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByIdController(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        CommentDTO commentByCommentId = this.commentService.getCommentByCommentId(postId, commentId);
        return new ResponseEntity<CommentDTO>(commentByCommentId, HttpStatus.OK);
    }

    @PutMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentController(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDTO commentDTO
    ) {

        CommentDTO updateComment = this.commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<CommentDTO>(updateComment, HttpStatus.OK);
    }

    @DeleteMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteCommentController(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {

        ApiResponse deleteComment = this.commentService.deleteComment(postId, commentId);
        return new ResponseEntity<ApiResponse>(deleteComment, HttpStatus.OK);

    }


}
