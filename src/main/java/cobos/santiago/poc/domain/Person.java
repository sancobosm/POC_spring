package cobos.santiago.poc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person{
    String person_ID;
    String name;
    String first;
    String last;
    String middle;
    String email;
    String phone;
    String fax;
    String title;
}

/*
public record Person(
        String person_ID,
        String name,
        String first,
        String last,
        String middle,
        String email,
        String phone,
        String fax,
        String title) {
}
*/
