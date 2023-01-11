package springjpa.hibernate.library.models;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message="Please, enter the name of the book")
    @Size(min=2, max=45, message = "The name of the book must contain from 2 to 45 letters")
    @Column(name = "name")
    private String name;
    @Min(value=1800, message="It's a library, not a museum!")
    @Max(value=2022, message = "We can't have any books from the future")
    @Column(name = "year")
    private int year;
    @NotEmpty(message = "Every book must have it's author!")
    @Size(max=50, message = "Try something simple. That should be less than 50 letters.")
    @Column(name = "author")
    private String author;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;
    @Column(name="taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    @Transient
    private boolean expired;


    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }
    public Book() {

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                '}';
    }
}
