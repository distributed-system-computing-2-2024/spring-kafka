package com.teamtwo.trafficsystem.batch;

import com.teamtwo.trafficsystem.domain.TrafficData;
import com.teamtwo.trafficsystem.utils.TrafficUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class TrafficBatch {
    @Autowired
    private TrafficUtils trafficUtils;

    @Autowired
    private KafkaTemplate<String, TrafficData> kafkaTemplate;

    @Bean
    public Job exchangeJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("exchangeJob", jobRepository)
            .start(step)
            .build();
    }
    @Bean
    public Step step(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("step", jobRepository)
            .tasklet(tasklet, platformTransactionManager).build();
    }
    @Bean
    public Tasklet tasklet(){
        return ((contribution, chunkContext) -> {
            List<TrafficData> trafficDataList = trafficUtils.getExchangeDataAsDtoList();

            for (TrafficData traffic : trafficDataList) {
                log.info("위치 : " + traffic.getConzoneName());
                kafkaTemplate.send("trafficSystem", traffic);
            }
            return RepeatStatus.FINISHED;
        });
    }
}
