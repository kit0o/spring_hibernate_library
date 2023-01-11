package springjpa.hibernate.library.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import springjpa.hibernate.library.models.Book;
import springjpa.hibernate.library.models.Person;
import springjpa.hibernate.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpa.hibernate.library.repositories.PeopleRepository;

import java.time.Instant;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }
    //Common findAll method below
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    //Below here is the method for pagination
    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }
    //Below here is the method for sorting books by year
    public List<Book> findAll(boolean sort) {
        return booksRepository.findAll(Sort.by("year"));
    }
    //And finally, below here is the method for both pagination and sorting books by year
    public List<Book> findAll(int page, int booksPerPage, boolean sort) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
    }
    public Book findOne(int id){
        Optional<Book> bookFound = booksRepository.findById(id);
        return bookFound.orElse(null);
    }
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
    @Transactional
    public void assign(Person personToAppoint, int id){
        Optional<Book> updatedBook = booksRepository.findById(id);
        updatedBook.orElse(null).setOwner(personToAppoint);
        updatedBook.orElse(null).setTakenAt(new Date());
        booksRepository.save(updatedBook.orElse(null));
    }
    @Transactional
    public void freeBook(int id) {
        Optional<Book> bookBack = booksRepository.findById(id);
        bookBack.orElse(null).setOwner(null);
        bookBack.orElse(null).setTakenAt(null);
        booksRepository.save(bookBack.orElse(null));
    }
    public List<Book> searchFor(String startingWith){
        return booksRepository.findByNameStartingWith(startingWith);
    }
}
