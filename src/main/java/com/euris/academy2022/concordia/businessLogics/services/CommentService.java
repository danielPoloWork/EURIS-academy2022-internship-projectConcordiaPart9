package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {

    Optional<Comment> getById(String id);
    List<Comment> getAll();


    Optional<Comment> insert(Comment comment);
    Optional<Comment> update(Comment comment);
    Boolean deleteById(String id);
    Boolean deleteAll();
}
