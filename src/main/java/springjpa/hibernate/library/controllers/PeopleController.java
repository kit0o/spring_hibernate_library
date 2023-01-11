package springjpa.hibernate.library.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import springjpa.hibernate.library.models.Book;
import springjpa.hibernate.library.models.Person;
import springjpa.hibernate.library.services.BooksService;
import springjpa.hibernate.library.services.PeopleService;
import springjpa.hibernate.library.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator  personValidator;

    private final PeopleService peopleService;
    private final BooksService booksService;

    public PeopleController(PersonValidator personValidator, PeopleService peopleService, BooksService booksService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, Model model2,
                       @ModelAttribute("book") Book book, @ModelAttribute("person") Person person) {
        model.addAttribute(peopleService.findOne(id));
        model2.addAttribute("library", booksService.findAll());
        List<Book> forOne = peopleService.findOne(id).getBooks();
        model2.addAttribute("books", peopleService.checkBooksWhetherPresent(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newMember(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
