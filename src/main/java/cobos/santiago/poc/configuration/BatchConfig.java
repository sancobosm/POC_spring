package cobos.santiago.poc.configuration;

import cobos.santiago.poc.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class BatchConfig {
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                     RepositoryItemWriter<Person> repositoryItemWriter, PersonProcessor processor, PersonFieldMapper personFieldMapper) {
        return new StepBuilder("myStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(PersonItemReader.configReader(personFieldMapper))
                .processor(processor)
                .writer(repositoryItemWriter)
                .build();
    }

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        return new JobBuilder("myJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Person> writer(PersonRepository personRepository) {
        RepositoryItemWriter<Person> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(personRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }
}