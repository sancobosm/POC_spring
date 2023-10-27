package cobos.santiago.poc.configuration;

import cobos.santiago.poc.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
@Slf4j
public class BatchConfig {
   /* @Value("")
    @Bean
    @Qualifier("myParameter")
    public JobParameters jobParameters(){
        return new JobParametersBuilder()
                .addString("input.file","people.csv")
                .toJobParameters();
    }*/
    private static final String FIELD_DELIMITER = ",";

    @Bean
    public FlatFileItemReader<Person> flatFileItemReader(PersonFieldMapper personFieldMapper,
                                                         @Value("${input.file}") String parameter) {
        return new FlatFileItemReaderBuilder<Person>()
                .name("Person item reader")
                .fieldSetMapper(personFieldMapper)
                .targetType(Person.class)
                .delimited()
                .delimiter(FIELD_DELIMITER)
                .names(Arrays.stream(PersonCsvFields.values())
                        .map(PersonCsvFields::getName)
                        .toArray(String[]::new)
                ).strict(true)
                .resource(new ClassPathResource(parameter))
                .linesToSkip(1)
                .recordSeparatorPolicy(new DefaultRecordSeparatorPolicy())
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                     RepositoryItemWriter<Person> repositoryItemWriter, PersonProcessor processor,
                     FlatFileItemReader<Person> flatFileItemReader) {
        return new StepBuilder("myStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(flatFileItemReader)
                .processor(processor)
                .writer(repositoryItemWriter)
                .build();
    }

    @Bean
    @Qualifier(value = "myJob")
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