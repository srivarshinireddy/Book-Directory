package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooksWithAuthors() {
        Author author = new Author("Author 1", "US");
        Book book1 = new Book("Book 1", 10.0, author);
        Book book2 = new Book("Book 2", 15.0, author);
        
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = libraryService.getAllBooksWithAuthors();

        assertThat(result).hasSize(2);
        verify(bookRepository, times(1)).findAllBooksWithAuthors();
    }

    @Test
    public void testSaveBook_Success() {
        Author author = new Author("Author 1", "US");
        author.setId(1L);
        Book book = new Book("Book 1", 10.0, author);
        
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = libraryService.saveBook(book);

        assertThat(savedBook.getTitle()).isEqualTo("Book 1");
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testSaveBook_InvalidAuthor_ThrowsException() {
        Author author = new Author("Author 1", "US");
        author.setId(99L);
        Book book = new Book("Book 1", 10.0, author);
        
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            libraryService.saveBook(book);
        });

        verify(authorRepository, times(1)).findById(99L);
        verify(bookRepository, never()).save(any(Book.class));
    }
}
