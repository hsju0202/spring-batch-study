package com.hsj.SpringBatchTest.jobs;

import com.hsj.SpringBatchTest.tasklets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TutorialConfig {

    private final JobBuilderFactory jobBuilderFactory; // Job 빌더 생성용
    private final StepBuilderFactory stepBuilderFactory; // Step 빌더 생성용

    // JobBuilderFactory를 통해서 tutorialJob을 생성
    @Bean
    public Job tutorialJob() {
        return jobBuilderFactory.get("tutorialJob")
                .start(tutorialStep1())  // Step 설정
                .next(tutorialStep2())
                .build();
    }

    // StepBuilderFactory를 통해서 tutorialStep을 생성
    @Bean
    public Step tutorialStep() {
        return stepBuilderFactory.get("tutorialStep")
                .tasklet(new TutorialTasklet()) // Tasklet 설정
                .build();
    }

    @Bean
    public Step tutorialStep1() {
        return stepBuilderFactory.get("tutorialStep1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("I'm a tutorialStep1");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step tutorialStep2() {
        return stepBuilderFactory.get("tutorialStep2")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("I'm a tutorialStep2");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
