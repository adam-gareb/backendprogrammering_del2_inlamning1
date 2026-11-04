package se.yrgo.inlamning1.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.yrgo.inlamning1.domain.Book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    private static List<Book> bookList = List.of(
            new Book("The Maze Runner", "James Dashner", "Sci-fi"),
            new Book("The Scorch Trials", "James Dashner", "Sci-fi"),
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "Tragedy"),
            new Book("The Lord of the Rings", "J.R.R Tolkien", "Fantasy"));

    @GetMapping("/home")
    public String showHome(Model model) {

        model.addAttribute("date", LocalDate.now().toString());

        return "home";
    }

    @GetMapping("/books")
    public String showBookList(Model model) {

        model.addAttribute("books", bookList);

        return "booklist";
    }

    @GetMapping("/recommended")
    public String showRecommended(Model model) {

        List<String> wanted = List.of("The Maze Runner", "The Lord of the Rings");

        List<Book> recommended = bookList.stream()
                .filter(book -> wanted.contains(book.getTitle()))
                .toList();

        model.addAttribute("books", recommended);

        return "recommended";
    }

    @GetMapping("/genre")
    public String showGenre(Model model, @RequestParam(name = "type", required = false) String genre) {

        List<String> genres = bookList.stream()
                .map(Book::getGenre)
                .distinct()
                .sorted()
                .toList();
        model.addAttribute("genres", genres);

        if (genre != null && !genre.isBlank()) {
            List<Book> booksOfGenre = bookList.stream()
                    .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                    .toList();

            model.addAttribute("genre", genre);
            model.addAttribute("books", booksOfGenre);
        }

        return "genre";
    }
}
