package com.euris.academy2022.concordia.configurations;

import com.euris.academy2022.concordia.businessLogics.services.*;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloLabelService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloMemberService;
import com.euris.academy2022.concordia.configurations.schedulings.TaskScheduling;
import com.euris.academy2022.concordia.configurations.schedulings.CommentScheduling;
import com.euris.academy2022.concordia.configurations.schedulings.MemberScheduling;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.*;
import static com.euris.academy2022.concordia.utils.prints.BatchProcessingPrint.*;

@Configuration
@EnableScheduling
public class BatchProcessingCfg {

    @Autowired private CommentService commentService;
    @Autowired private MemberService memberService;
    @Autowired private TaskService taskService;
    @Autowired private TrelloCardService trelloCardService;
    @Autowired private TrelloCommentService trelloCommentService;
    @Autowired private TrelloLabelService trellolabelService;
    @Autowired private TrelloMemberService trelloMemberService;

    @Scheduled(cron = "* * 1 * * *") /* Every day at 1:00 AM */
    public void fetchExpiringTask() {
        printFetchExpiringTaskStart(Thread.currentThread().getName());
        taskService.updateExpiringTasks();
        printFetchExpiringTaskEnd(Thread.currentThread().getName());
    }

    @Scheduled(cron = "* * * * * *") /* Every second, just for testing */
    public void synchronize() {
        fetchTrelloAndPullToConcordia();
        //fetchConcordiaAndPushToTrello();
        //TODO:generateReportAndSendEmail()
    }

    private void fetchTrelloAndPullToConcordia() {
        printFetchTrelloStart(Thread.currentThread().getName());
        MemberScheduling.fetchAndPull(trelloMemberService, memberService, ID_BOARD_VALUE);
        TaskScheduling.fetchAndPull(trelloCardService, taskService, TaskStatus.TO_DO);
        TaskScheduling.fetchAndPull(trelloCardService, taskService, TaskStatus.IN_PROGRESS);
        TaskScheduling.fetchAndPull(trelloCardService, taskService, TaskStatus.COMPLETED);
        CommentScheduling.fetchAndPull(trelloCommentService, commentService, memberService);
        printFetchTrelloEnd(Thread.currentThread().getName());
    }

    private void fetchConcordiaAndPushToTrello() {
        printFetchConcordiaStart(Thread.currentThread().getName());
        TaskScheduling.fetchAndPush(trelloCardService, trellolabelService, taskService);
        CommentScheduling.fetchAndPush(trelloCommentService, commentService, taskService);
        printFetchConcordiaEnd(Thread.currentThread().getName());
    }

    //TODO:private void generateReportAndSendEmail(){}
}