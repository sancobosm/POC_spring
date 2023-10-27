package cobos.santiago.poc.configuration;

import cobos.santiago.poc.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfiguration {
    @Bean
    public ItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("PersonItemReader")
                .resource(new ClassPathResource("src/main/resources/people.csv"))
                .delimited()
                .names("person_ID","name","first","last","middle","email","phone","fax","title")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return item -> item; // Perform any necessary processing here
    }

    @Bean
    public ItemWriter<Person> writer() {
        return items -> {
            for (Person item : items) {
                // Write item to the destination
                log.info("Writing item: {}", item);
            }
        };
    }

    @Bean
    public Step step(ItemReader<Person> reader, ItemProcessor<Person, Person> processor,
                     ItemWriter<Person> writer, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep",jobRepository)
                .<Person, Person>chunk(10,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        return new JobBuilder("myJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }
}