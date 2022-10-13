package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.DTOs.jasperDTOs.MemberTaskTuple;
import com.euris.academy2022.concordia.dataPersistences.DTOs.jasperDTOs.TaskProgress;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public interface PdfService {

    public boolean taskAdvancement() throws JRException, FileNotFoundException;

    public boolean memberPerformanceReport() throws JRException, FileNotFoundException;
}
