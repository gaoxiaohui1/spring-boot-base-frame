package com.base.frame.api.controller;

import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.springbatch.JobLaunchService;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class BatchJobController {
    @Autowired
    private JobLaunchService jobLauncherService;


    @RequestMapping(path = "excuteJob", method = RequestMethod.GET)
    public BaseResult excuteJob(String jobName) {
        JobExecution jobExecution = jobLauncherService.launchJob(jobName);
        System.out.println(jobExecution.getStatus());
        BaseResult result= ResultGenerator.success();
        result.setMessage(jobExecution.getStatus().toString());
        return result;
    }

    @RequestMapping(path = "excuteJobTest", method = RequestMethod.GET)
    public BaseResult excuteJobTest(String jobName) {
        BaseResult result= ResultGenerator.success();
        result.setMessage("jobExecution.getStatus().toString()");
        return result;
    }
}
