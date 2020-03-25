package com.base.frame.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class JobLaunchService {
    @Autowired
    private Job csv2dbJob;
    @Autowired
    private Job db2csvJob;
    @Autowired
    private Job db2dbJob;
    @Autowired
    private JobLauncher jobLauncher;

    public JobExecution launchJob(String jobName) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("timestamp", Calendar.getInstance().getTime())
                    .toJobParameters();
            Job job = csv2dbJob;
            switch (jobName) {
                case "db2csvJob":
                    job = db2csvJob;
                    break;
                case "db2dbJob":
                    job = db2dbJob;
                    break;
                default:
                    job = csv2dbJob;
                    break;
            }
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            return jobExecution;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("launch job exception ", e);
        }
    }
}
