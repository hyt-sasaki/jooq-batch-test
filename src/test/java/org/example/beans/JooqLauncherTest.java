package org.example.beans;

import lombok.val;
import org.example.beans.tasks.CityDTO;
import org.example.beans.tasks.JooqWriter;
import org.example.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBatchTest
@ContextConfiguration(classes = AppConfig.class)
class JooqLauncherTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @SpyBean
    private JooqWriter jooqWriter;
    @Captor
    private ArgumentCaptor<List<CityDTO>> itemCaptor;

    @BeforeEach
    void clearJobExecutions() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void test() {
        // given
        // sakilaのcityのレコード数: 600, chunkSize: 3

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("jooqStep", defaultJobParameters());
        val actualStepExecutions = jobExecution.getStepExecutions();
        val actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertThat(actualStepExecutions.size()).isEqualTo(1);
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");
        verify(jooqWriter, times(200)).write(itemCaptor.capture());
        itemCaptor.getAllValues().forEach(chunkItems -> assertThat(chunkItems).hasSize(3));
    }


    private JobParameters defaultJobParameters() {
        return new JobParametersBuilder().toJobParameters();
    }
}