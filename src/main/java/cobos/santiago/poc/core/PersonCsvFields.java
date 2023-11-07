package cobos.santiago.poc.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PersonCsvFields {
    PERSON_ID(0,"person_id"),
    NAME(1,"name"),
    FIRST(2,"first"),
    LAST(3,"last"),
    MIDDLE(4,"middle"),
    EMAIL(5,"email"),
    PHONE(6,"phone"),
    TITLE(8,"title");

    private final int index;
    private final String name;
}
