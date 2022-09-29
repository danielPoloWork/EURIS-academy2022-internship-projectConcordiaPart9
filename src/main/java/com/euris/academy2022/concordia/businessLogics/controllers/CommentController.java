package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.request.comments.CommentPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.request.comments.CommentPutRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @GetMapping
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @GetMapping("/{idComment}")
    public Comment getById(@PathVariable String idComment) {
        return commentService.getById(idComment).get();
    }

    @PostMapping
    public Comment insert(@RequestBody CommentPostRequest comment) {
        return commentService.insert(comment.toModel()).get();
    }

    @PutMapping
    public Comment update(@RequestBody CommentPutRequest comment) {

        return commentService.update(comment.toModel()).get();
    }

    @DeleteMapping
    public Boolean deleteAll() {
        return commentService.deleteAll();
    }

    @DeleteMapping("/{idComment}")
    public Boolean delete(@PathVariable String idComment) throws Exception {
        return commentService.deleteById(idComment);
    }




}
