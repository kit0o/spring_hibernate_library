package springjpa.hibernate.library.models;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min=2, max=45, message = "Your name should be between 2 and 50 characters")
    @NotEmpty(message="Your name should not be empty!")
    @Column(name = "name")
    private String name;

    @Min(value = 1901, message="That's impossible. Please, enter your real birth year.")
    @Max(value = 2020, message="That's impossible. Please, enter your real birth year.")
    @Column(name = "birthdate")
    private int birthdate;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Book> books;

    public Person(String name, int birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }
    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
