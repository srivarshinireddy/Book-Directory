package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooksWithAuthors());
        return "list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "form";
    }

    @PostMapping
    public String saveBook(@ModelAttribute("book") Book book, Model model) {
        try {
            libraryService.saveBook(book);
            return "redirect:/books";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Data integrity violation: Ensure all fields are valid.");
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "form";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Book book = libraryService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "form";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book, Model model) {
        try {
            book.setId(id);
            libraryService.saveBook(book);
            return "redirect:/books";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Data integrity violation: Ensure all fields are valid.");
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "form";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "form";
        }
    }
}
