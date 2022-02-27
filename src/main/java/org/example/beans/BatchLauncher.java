package org.example.beans;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BatchLauncher {

    @NonNull
    private final JobLauncher jobLauncher;

    @NonNull
    private final Job job;

    public void launch() {
        val params = new HashMap<String, JobParameter>();
        val uuid = UUID.randomUUID();
        params.put("jobKey", new JobParameter(uuid.toString()));

        val jobParameters = new JobParameters(params);

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobRestartException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
