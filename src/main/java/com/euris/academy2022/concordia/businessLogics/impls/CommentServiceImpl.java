package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.jpaRepositories.CommentJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentJpaRepository commentJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TaskJpaRepository taskJpaRepository;

    public CommentServiceImpl(CommentJpaRepository commentJpaRepository, MemberJpaRepository memberJpaRepository, TaskJpaRepository taskJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Optional<Comment> getById(String id) {

        return commentJpaRepository.findById(id);
    }

    @Override
    public List<Comment> getAll() {

        return commentJpaRepository.findAll();
    }


    @Override
    public Optional<Comment> insert(Comment comment) {
        Optional<Comment> optionalComment = commentJpaRepository.findById(comment.getUuid());
        if (optionalComment.isEmpty() &&
                taskJpaRepository.existsById(comment.getTask().getId()) &&
                memberJpaRepository.existsById(comment.getMember().getId())) {
            return Optional.of(commentJpaRepository.save(comment));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Comment> update(Comment comment) {
        Optional<Comment> optionalComment = commentJpaRepository.findById(comment.getUuid());

        if (optionalComment.isPresent()) {
            comment.setMember(optionalComment.get().getMember());
            comment.setTask(optionalComment.get().getTask());
            return Optional.of(commentJpaRepository.save(comment));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(String id) {
        commentJpaRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteAll() {
        commentJpaRepository.deleteAll();
        return Boolean.TRUE;
    }
}
