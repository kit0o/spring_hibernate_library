package springjpa.hibernate.library.services;
import org.hibernate.Hibernate;
import springjpa.hibernate.library.models.Book;
import springjpa.hibernate.library.models.Person;
import springjpa.hibernate.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    public Person findOne(int id){
        Optional <Person> foundPerson = peopleRepository.findById(id);
        System.out.println(foundPerson.orElse(null).getBooks());
        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    //The method below is created for Validator concerns
    public Person findOne(String name) {
        Person foundByName = peopleRepository.findByName(name);
        return foundByName;
    }
    //Here is the method for searching and detecting expired books with the person id below
    public List<Book> checkBooksWhetherPresent(int id) {
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null) {
            List<Book> booksTaken = person.getBooks();
            for(Book book : booksTaken) {
                long difference = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (difference > 864000000)
                    book.setExpired(true);
            }
            return person.getBooks();
        }
        else return Collections.emptyList();
    }

}
