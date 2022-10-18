package com.euris.academy2022.concordia.configurations;

import com.euris.academy2022.concordia.businessLogics.services.*;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloLabelService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloMemberService;
import com.euris.academy2022.concordia.configurations.synchronizations.TaskSync;
import com.euris.academy2022.concordia.configurations.synchronizations.CommentSync;
import com.euris.academy2022.concordia.configurations.synchronizations.MemberSync;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.*;
import static com.euris.academy2022.concordia.utils.prints.BatchProcessingPrint.*;

@Configuration
@EnableScheduling
public class BatchProcessingCfg {

    @Autowired private CommentService commentService;
    @Autowired private ConfigurationService configurationService;
    @Autowired private EmailService emailService;
    @Autowired private MemberService memberService;
    @Autowired private TaskService taskService;
    @Autowired private TrelloCardService trelloCardService;
    @Autowired private TrelloCommentService trelloCommentService;
    @Autowired private TrelloLabelService trelloLabelService;
    @Autowired private TrelloMemberService trelloMemberService;

    @Scheduled(cron = "${cron.expression.expiring}") /* Every day at 1:00 AM */
    public void fetchExpiringTask() {
        printFetchExpiringTaskStart(Thread.currentThread().getName());
        taskService.updateExpiringTasks();
        printFetchExpiringTaskEnd(Thread.currentThread().getName());
    }

    @Scheduled(cron = "${cron.expression.sync}") /* Every second, just for testing */
    public void synchronize() {
        //fetchTrelloAndPullToConcordia();
        //fetchConcordiaAndPushToTrello();
    }

    private void fetchTrelloAndPullToConcordia() {
        printFetchTrelloStart(Thread.currentThread().getName());
        MemberSync.fetchAndPull(trelloMemberService, memberService, configurationService.getByLabel(ID_BOARD_VALUE).getBody().getValue());
        TaskSync.fetchAndPull(trelloCardService, taskService, TaskStatus.TO_DO);
        TaskSync.fetchAndPull(trelloCardService, taskService, TaskStatus.IN_PROGRESS);
        TaskSync.fetchAndPull(trelloCardService, taskService, TaskStatus.COMPLETED);
        CommentSync.fetchAndPull(trelloCommentService, commentService, memberService);
        printFetchTrelloEnd(Thread.currentThread().getName());
    }

    private void fetchConcordiaAndPushToTrello() {
        printFetchConcordiaStart(Thread.currentThread().getName());
        TaskSync.fetchAndPush(trelloCardService, trelloLabelService, taskService);
        CommentSync.fetchAndPush(trelloCommentService, commentService, taskService);
        printFetchConcordiaEnd(Thread.currentThread().getName());
    }
}