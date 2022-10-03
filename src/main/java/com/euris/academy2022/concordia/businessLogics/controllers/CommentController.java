package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;

import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.comments.CommentPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @GetMapping
    public ResponseDto<List<Comment>> getAll() {
        return commentService.getAll();
    }

    @GetMapping("/{Uuid}")
    public ResponseDto<Comment> getByUuid(@PathVariable String Uuid) {
        return commentService.getByUuid(Uuid);
    }

    @PostMapping
    public ResponseDto<Comment> insert(@RequestBody CommentPostRequest comment) {
        return commentService.insert(comment.toModel());
    }

    @PutMapping
    public ResponseDto<Comment> update(@RequestBody CommentPutRequest comment) {

        return commentService.update(comment.toModel());
    }



    @DeleteMapping("/{Uuid}")
    public ResponseDto<Comment> delete(@PathVariable String Uuid) {
        return commentService.deleteByUuid(Uuid);
    }




}
