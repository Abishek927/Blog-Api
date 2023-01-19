package com.api.blog.Service;

import com.api.blog.Payloads.ApiResponse;
import com.api.blog.Payloads.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentByPost(Long postId);

    CommentDTO getCommentByCommentId(Long postId, Long commentId);

    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

    ApiResponse deleteComment(Long postId, Long commentId);

}
