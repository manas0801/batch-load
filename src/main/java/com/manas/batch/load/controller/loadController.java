package com.manas.batch.load.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class loadController {

    @Autowired
    public JobLauncher jobLauncher;

    @Autowired
    public Job job;

    @Autowired
    public Job UnloadJob;



    @GetMapping("/load")
    public BatchStatus BatchLoad() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobRestartException,
            JobParametersInvalidException {
        Map<String,JobParameter> jpm = new HashMap<>();
        JobParameter jp = new JobParameter(new Date(20190801));
        jpm.put("Date",jp);
        JobParameters jps = new JobParameters (jpm);
        JobExecution je= jobLauncher.run(job,jps);

        while (je.isRunning()){
            System.out.println("Job is running ..");
        }

        return je.getStatus();


    }

    @GetMapping("/unload")
    public BatchStatus BatchUnload() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobRestartException,
            JobParametersInvalidException {
        Map<String,JobParameter> jpm = new HashMap<>();
        JobParameter jp = new JobParameter(new Date(20190801));
        jpm.put("Date",jp);
        JobParameters jps = new JobParameters (jpm);
        JobExecution je= jobLauncher.run(UnloadJob,jps);

        while (je.isRunning()){
            System.out.println("Job is running ..");
        }

        return je.getStatus();


    }
}
