# Project Approach and Implementation Details

## 1. Entity Relationship Design

For this application, I chose the entities **Author** and **Book**.
The relationship is **One-To-Many** from Author to Book (and Many-To-One from Book to Author).
- `Author` has attributes: `id` (Primary Key), `name`, `nationality`, and a collection of `Book`s.
- `Book` has attributes: `id` (Primary Key), `title`, `price`, and a reference to its `Author`.

This relationship was mapped using JPA annotations `@Entity`, `@Id`, `@OneToMany`, and `@ManyToOne`.

## 2. Implementation Details for Each Operation

### Populate Database
The database is an in-memory H2 database. Data is populated automatically on startup via a `data.sql` script located in `src/main/resources`. It inserts 10 sample authors and 10 sample books, fulfilling the requirements.

### Create Operation
- **View**: Implemented in `form.jsp`. A simple form allows users to input the title, price, and select an author from a dropdown.
- **Controller**: The `LibraryController` handles the `POST /books` request. It calls `libraryService.saveBook()`.
- **Exception Handling**: Data integrity violations (e.g., submitting a book with invalid/missing author) are caught and handled by returning the user to the form with a descriptive error message.

### Read Operation
- **View**: Implemented in `list.jsp`. It uses JSTL (`<c:forEach>`) to display a table of all books along with their associated authors.
- **Repository Custom Query**: To optimize data fetching and fulfill the requirement, `BookRepository` includes a custom query method:
  `@Query("SELECT b FROM Book b JOIN FETCH b.author")`
  This performs an inner join fetch to avoid N+1 queries.
- **Controller**: Handled by `GET /books`, which fetches the data from the service layer and binds it to the model.

### Update Operation
- **View**: Uses the same `form.jsp` view, making it reusable. The form dynamically populates with the existing book's details.
- **Controller**: `GET /books/edit/{id}` displays the form. `POST /books/update/{id}` processes the update request and saves the updated entity.

## 3. Testing
Unit tests were implemented using JUnit 5 and Mockito.
- `LibraryServiceTest` tests the business logic and exception handling without hitting the database, mocking the repository layer.
- `BookRepositoryTest` uses `@DataJpaTest` to verify that the custom join query works as expected against the in-memory database.

## 4. Challenges Faced and Solutions
- **Handling Exception Rendering in JSP**: To smoothly handle validation errors/integrity violations without crashing the application, a `try-catch` block was added in the controller logic for both create and update operations. On failure, it binds an `error` message to the model and returns the form view instead of an error page.
- **JSP view mapping in Spring Boot**: Spring Boot prefers Thymeleaf over JSP. To use JSP, `tomcat-embed-jasper` and JSTL dependencies had to be configured correctly in `pom.xml`, and the view resolver prefix/suffix properties were set in `application.properties`.

## 5. Github URL
*(User to replace with their GitHub repository URL)*
https://github.com/username/repository-name
