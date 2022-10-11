package com.euris.academy2022.concordia.configurations;

import com.euris.academy2022.concordia.businessLogics.services.*;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloMemberService;
import com.euris.academy2022.concordia.configurations.schedulings.TaskScheduling;
import com.euris.academy2022.concordia.configurations.schedulings.CommentScheduling;
import com.euris.academy2022.concordia.configurations.schedulings.MemberScheduling;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.*;

@Configuration
@EnableScheduling
public class BatchProcessingCfg {

    @Autowired private CommentService commentService;
    @Autowired private MemberService memberService;
    @Autowired private TaskService taskService;
    @Autowired private TrelloCardService trelloCardService;
    @Autowired private TrelloCommentService trelloCommentService;
    @Autowired private TrelloMemberService trelloMemberService;


    @Scheduled(cron = "* * 1 * * *") /* Every day at 1:00 AM */
    public void fetchExpiringTask() {
        System.out.println("       ───────────────────────────────────────────────────");
        System.out.println("       :: Concordia Scheduling ::         (FETCH EXPIRING)\n");

        taskService.updateExpiringTasks();

        System.out.printf("\n%s  [fetchExpiring ] executed at %s  INFO : STARTED..\n",
            Thread.currentThread().getName(),
            new Date());
    }

    @Scheduled(cron = "* * * * * *") /* Every day at 1:00 AM */
    public void fetchTrello() {
        System.out.println("       ───────────────────────────────────────────────────");
        System.out.println("       :: Concordia Scheduling ::           (FETCH TRELLO)\n");

        System.out.printf("%s  [fetchTrello   ] executed at %s  INFO : STARTED..\n",
                Thread.currentThread().getName(),
                new Date());

        MemberScheduling.fetchAndPull(trelloMemberService, memberService, ID_BOARD_VALUE);
        TaskScheduling.fetchAndPull(trelloCardService, taskService, TaskStatus.TO_DO);
        /* [Start] Only 1st fetch if some cards are present on other lists than TO_DO */
        TaskScheduling.fetchAndPull(trelloCardService, taskService, TaskStatus.IN_PROGRESS);
        TaskScheduling.fetchAndPull(trelloCardService, taskService, TaskStatus.COMPLETED);
        /* [End] */
        CommentScheduling.fetchAndPull(trelloCommentService, commentService, memberService);
    }

//    @Scheduled(cron = "* * * * * *") /* Every day at 1:00 AM */
//    public void fetchConcordia() {
//        System.out.println("       ───────────────────────────────────────────────────");
//        System.out.println("       :: Concordia Scheduling ::        (FETCH CONCORDIA)\n");
//
//        System.out.printf("\n%s  [fetchConcordia] executed at %s  INFO : STARTED..\n",
//                Thread.currentThread().getName(),
//                new Date());
//    }
}