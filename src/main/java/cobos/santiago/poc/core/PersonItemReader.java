package cobos.santiago.poc.core;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@StepScope
public class PersonItemReader extends FlatFileItemReader<Person> {
    private static final String PERSON_CSV = "people.csv";
    private static final String FIELD_DELIMITER = ",";

    public static FlatFileItemReader<Person> configReader(PersonFieldMapper personFieldMapper) {
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
                .resource(new ClassPathResource(PERSON_CSV))
                .linesToSkip(1)
                .recordSeparatorPolicy(new DefaultRecordSeparatorPolicy())
                .build();
    }

}
