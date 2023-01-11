package springjpa.hibernate.library.controllers;

import springjpa.hibernate.library.models.Book;
import springjpa.hibernate.library.models.Person;
import springjpa.hibernate.library.services.BooksService;
import springjpa.hibernate.library.services.PeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }
    //Common findAll method below
    @GetMapping()
    public String index(Model model){
        model.addAttribute("library",booksService.findAll());
        return "book/index";
    }
    //Below here is the method for pagination
    @GetMapping(params = {"page","booksPerPage"})
    public String indexPagination(@RequestParam("page") int page, @RequestParam("booksPerPage") int booksPerPage,
                                  Model model) {
        model.addAttribute("library", booksService.findAll(page, booksPerPage));
        return "book/index";
    }
    //Below here is the method for sorting books by year
    @GetMapping(params = {"sort_by_year"})
    public String indexSorted(@RequestParam("sort_by_year") boolean sort, Model model) {
        model.addAttribute("library", booksService.findAll(sort));
        return "book/index";
    }
    //And finally, below here is the method for both pagination and sorting books by year
    @GetMapping(params = {"page", "booksPerPage", "sort_by_year"})
    public String indexPaginatedAndSorted(@RequestParam("page") int page, @RequestParam("booksPerPage") int booksPerPage,
                                          @RequestParam("sort_by_year") boolean sort,
                                          Model model) {
        model.addAttribute("library", booksService.findAll(page, booksPerPage, sort));
        return "book/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, Model model1, @ModelAttribute("person") Person person){
        model1.addAttribute("people", peopleService.findAll());
        model.addAttribute(booksService.findOne(id));
        return "book/show";
    }
    @PatchMapping("/appoint/{id}")
    public String bookToGive(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                             @ModelAttribute("book") Book book){
        booksService.assign(person, id);
        return "book/luckyPage";
    }
    @PatchMapping("/free/{id}")
    public String freeButton(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.freeBook(id);
        return "book/luckyFree";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "book/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "book/new";
        }
        booksService.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "book/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id")int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "book/edit";
        }
        booksService.update(id, book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/book";
    }
    @GetMapping("/searchPage")
    public String searchingPage(Model model){
        model.addAttribute("library", booksService.findAll());
        return "book/searchPage";
    }
    @GetMapping(value = "/doTheSearch", params = {"search"})
    public String doTheSearch(@RequestParam("search") String search, Model model, @ModelAttribute("book") Book book){
        List<Book> foundBooks = booksService.searchFor(search);
        model.addAttribute("foundBooks", foundBooks);
        model.addAttribute("search", search);
        return "book/searchPage";
    }

}
