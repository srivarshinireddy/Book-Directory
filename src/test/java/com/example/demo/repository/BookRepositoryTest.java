package com.example.demo.repository;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindAllBooksWithAuthors() {
        // Arrange
        Author author = new Author("Test Author", "Testland");
        author = authorRepository.save(author);

        Book book = new Book("Test Book", 10.0, author);
        bookRepository.save(book);

        // Act
        List<Book> books = bookRepository.findAllBooksWithAuthors();

        // Assert
        assertThat(books).isNotEmpty();
        // Check if our test book is in the list
        boolean found = books.stream().anyMatch(b -> b.getTitle().equals("Test Book") && b.getAuthor().getName().equals("Test Author"));
        assertThat(found).isTrue();
    }
}
