package com.euris.academy2022.concordia.businessLogics.services;


import com.euris.academy2022.concordia.businessLogics.services.impls.CommentServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.jpaRepositories.CommentJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.properties")
public class CommentServiceTest {

    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private TaskJpaRepository taskJpaRepository;
    private CommentService commentService;

    @BeforeEach
    public void init() {
        this.commentService = new CommentServiceImpl(commentJpaRepository, memberJpaRepository, taskJpaRepository);
    }

    @Test
    public void getAllWorks(){

        commentService.insert(new Comment());

    }


}
