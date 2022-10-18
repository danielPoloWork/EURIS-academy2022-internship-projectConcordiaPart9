package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;

import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseDto<CommentDto> insert(@RequestBody CommentPostRequest comment) {
        return commentService.insert(comment.toModel());
    }
    @PutMapping
    public ResponseDto<CommentDto> update(@RequestBody CommentPutRequest comment) {
        return commentService.update(comment.toModel());
    }
    @DeleteMapping("/{Uuid}")
    public ResponseDto<CommentDto> removeByUuid(@PathVariable String Uuid) {
        return commentService.removeByUuid(Uuid);
    }
    @GetMapping
    public ResponseDto<List<CommentDto>> getAll() {
        return commentService.getAll();
    }
    @GetMapping("/{Uuid}")
    public ResponseDto<CommentDto> getByUuid(@PathVariable String Uuid) {
        return commentService.getByUuid(Uuid);
    }
    @GetMapping("/idTask={idTask}")
    ResponseDto<List<CommentDto>> getAllByIdTask(@PathVariable String idTask) {
        return commentService.getAllByIdTask(idTask);
    }
}