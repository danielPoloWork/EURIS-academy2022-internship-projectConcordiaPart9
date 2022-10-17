package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.PdfService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.jasperDTOs.MemberTaskTuple;
import com.euris.academy2022.concordia.dataPersistences.DTOs.jasperDTOs.TaskProgress;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private TaskJpaRepository taskJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    public PdfServiceImpl() {


    }


    public boolean taskAdvancement() throws JRException, FileNotFoundException {

        TaskProgress progress = TaskProgress.builder()
                .completed(taskJpaRepository.countByStatus(TaskStatus.COMPLETED))
                .inprogress(taskJpaRepository.countByStatus(TaskStatus.IN_PROGRESS))
                .todo(taskJpaRepository.countByStatus(TaskStatus.TO_DO)).build();


        JasperReport jasperReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/tasks.jrxml"));


        Map<String, Object> parameters = new HashMap<>();
        List<TaskProgress> progressList = new ArrayList<>();
        progressList.add(progress);
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(progressList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "tasks.pdf");


        return true;
    }


    private List<MemberTaskTuple> completed() {

        List<Member> memberList = memberJpaRepository.findAll();
        List<MemberTaskTuple> userTasksPercentage = new ArrayList<>();

        for (Member m : memberList) {

            List<Task> taskList = taskJpaRepository.findAllTasksByMemberUuid(m.getUuid());
            int completed = 0;
            int total = taskList.size();
            for (Task t : taskList) {
                if (t.getStatus().toString().equals("COMPLETED")) {
                    completed++;
                }
            }
            float percentage = (float) 0;

            if (total != 0) {
                percentage = ((float) completed / (float) total) * 100;
            }
            MemberTaskTuple user = MemberTaskTuple.builder()
                    .username(m.getUsername())
                    .taskNumber(percentage).build();
            userTasksPercentage.add(user);
        }
        userTasksPercentage = userTasksPercentage.stream()
                .sorted().collect(Collectors.toList());

        return userTasksPercentage;
    }


    public boolean memberPerformanceReport() throws JRException, FileNotFoundException {

        JasperReport jasperReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/MemberPerformances.jrxml"));


        Map<String, Object> parameters = new HashMap<>();
        List<MemberTaskTuple> performances = completed();

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(performances);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "performances.pdf");

        return true;
    }


}
