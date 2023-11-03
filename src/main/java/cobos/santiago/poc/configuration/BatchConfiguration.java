package cobos.santiago.poc.configuration;

import cobos.santiago.poc.domain.Person;
import cobos.santiago.poc.domain.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class BatchConfiguration {
    @Bean
    @StepScope
    public FlatFileItemReader<Person> itemReader (){
        BeanWrapperFieldSetMapper<Person> personBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        personBeanWrapperFieldSetMapper.setTargetType(Person.class);

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("person_ID","name","first","last","middle","email","phone","fax","title");
        delimitedLineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5, 6, 7, 8);


        DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setFieldSetMapper(personBeanWrapperFieldSetMapper);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("people.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(defaultLineMapper);
        reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
        reader.setStrict(false);
        reader.setSaveState(true);

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Person, Person> processor() {
        return item -> item;
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Person> writer(PersonRepository personRepository) {
        RepositoryItemWriter<Person> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(personRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                     RepositoryItemWriter<Person> repositoryItemWriter, FlatFileItemReader<Person> flatFileItemReader, ItemProcessor<Person, Person> processor) {
        return new StepBuilder("myStep",jobRepository)
                .<Person, Person>chunk(10,transactionManager)
                .reader(flatFileItemReader)
                .processor(processor)
                .writer(repositoryItemWriter)
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