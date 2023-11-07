package cobos.santiago.poc.core;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import static cobos.santiago.poc.core.PersonCsvFields.*;

@Component
public class PersonFieldMapper implements FieldSetMapper<Person> {
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Person(
                fieldSet.readInt(PERSON_ID.getIndex()),
                fieldSet.readString(NAME.getIndex()),
                fieldSet.readString(FIRST.getIndex()),
                fieldSet.readString(LAST.getIndex()),
                fieldSet.readString(MIDDLE.getIndex()),
                fieldSet.readString(EMAIL.getIndex()),
                fieldSet.readString(PHONE.getIndex()),
                fieldSet.readString(TITLE.getIndex())

        );
    }
}
