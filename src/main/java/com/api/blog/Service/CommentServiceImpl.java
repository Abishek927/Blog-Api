package com.api.blog.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.Exception.ResourceNotFoundException;
import com.api.blog.Model.Comment;
import com.api.blog.Model.Post;
import com.api.blog.Payloads.ApiResponse;
import com.api.blog.Payloads.CommentDTO;
import com.api.blog.Repositories.CommentRepositories;
import com.api.blog.Repositories.PostRepositories;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepositories commentRepositories;
    @Autowired
    private PostRepositories postRepositories;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

        Post post = this.postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        Comment map = this.mapper.map(commentDTO, Comment.class);
        map.setPost(post);
        Comment save = this.commentRepositories.save(map);


        return this.mapper.map(save, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getAllCommentByPost(Long postId) {
        Post post = this.postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        List<Comment> comment = post.getComment();
        List<CommentDTO> commentDTOs = comment.stream().map(com -> this.mapper.map(com, CommentDTO.class)).collect(Collectors.toList());
        return commentDTOs;

    }

    @Override
    public CommentDTO getCommentByCommentId(Long postId, Long commentId) {
        CommentDTO resultCommentDTO = null;
        Post post = this.postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        Comment comment = this.commentRepositories.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", commentId));
        if (comment.getPost().equals(post)) {
            resultCommentDTO = this.mapper.map(comment, CommentDTO.class);
        }


        return resultCommentDTO;
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment save = null;
        Post post = this.postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        Comment comment = this.commentRepositories.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", commentId));
        if (comment.getPost().equals(post)) {
            comment.setCommentContent(commentDTO.getCommentContent());


            save = this.commentRepositories.save(comment);
        }

        return this.mapper.map(save, CommentDTO.class);
    }

    @Override
    public ApiResponse deleteComment(Long postId, Long commentId) {

        ApiResponse apiResponse = null;

        Post post = this.postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        Comment comment = this.commentRepositories.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", commentId));
        if (comment.getPost().equals(post)) {
            comment.setPost(null);
            this.commentRepositories.delete(comment);
            apiResponse = new ApiResponse("Successfully Deleted comment!!!", true);
        }


        return apiResponse;
    }

}
