package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.beans.tasks.CityDTO;
import org.example.beans.tasks.JooqReader;
import org.example.beans.tasks.JooqWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@Configuration
public class BatchJobConfig {
    @NonNull
    private StepBuilderFactory stepBuilderFactory;
    @NonNull
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Step jooqStep(@Autowired JooqReader jooqReader,
                         @Autowired JooqWriter jooqWriter) {
        return stepBuilderFactory.get("jooqStep")
                .<CityDTO, CityDTO> chunk(3)
                .reader(jooqReader)
                .writer(jooqWriter)
                .build();
    }

    @Bean("jooqJob")
    public Job jooqJob(@Autowired @Qualifier("jooqStep") Step jooqStep) {
        return jobBuilderFactory.get("jooqJob")
                .flow(jooqStep)
                .end()
                .build();
    }
}
