package cobos.santiago.poc.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private String person_ID;
    private String name;
    private String first;
    private String last;
    private String middle;
    private String email;
    private String phone;
    private String fax;
    private String title;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Person) obj;
        return Objects.equals(this.person_ID, that.person_ID) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.first, that.first) &&
                Objects.equals(this.last, that.last) &&
                Objects.equals(this.middle, that.middle) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.phone, that.phone) &&
                Objects.equals(this.fax, that.fax) &&
                Objects.equals(this.title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_ID, name, first, last, middle, email, phone, fax, title);
    }

    @Override
    public String toString() {
        return "Person[" +
                "person_ID=" + person_ID + ", " +
                "name=" + name + ", " +
                "first=" + first + ", " +
                "last=" + last + ", " +
                "middle=" + middle + ", " +
                "email=" + email + ", " +
                "phone=" + phone + ", " +
                "fax=" + fax + ", " +
                "title=" + title + ']';
    }

}

