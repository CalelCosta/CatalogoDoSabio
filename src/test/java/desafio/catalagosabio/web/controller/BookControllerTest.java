package desafio.catalagosabio.web.controller;

import desafio.catalagosabio.application.dto.BookDto;
import desafio.catalagosabio.application.service.BookService;
import desafio.catalagosabio.domain.exception.BusinessException;
import desafio.catalagosabio.infra.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static desafio.catalagosabio.domain.exception.ExceptionEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book1;
    private BookDto bookDto1;
    private BookDto bookDto2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book1 = Book.builder()
                .id(1L)
                .author("Author One")
                .genre("Fiction")
                .description("Hardcover")
                .title("Book One")
                .build();

        bookDto1 = BookDto.builder()
                .id(1L)
                .author("Author One")
                .genre("Fiction")
                .description("Hardcover")
                .title("Book One")
                .build();
         bookDto2 = BookDto.builder()
                .id(1L)
                .author("Author Two")
                .genre("Non-Fiction")
                .description("Paperback")
                .title("Book Two")
                .build();
    }

    @Test
    void testGetAllBooks() {
        Page<BookDto> booksPage = new PageImpl<>(Arrays.asList(bookDto1, bookDto2), PageRequest.of(0, 10), 2);
        when(bookService.findAllBooks(any())).thenReturn(booksPage);

        ResponseEntity<Page<BookDto>> response = bookController.getAllBooks(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());

        verify(bookService, times(1)).findAllBooks(any());
    }

    @Test
    void testGetBookById_Success() {

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book1));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book One", response.getBody().getTitle());

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookService.getBookById(3L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookController.getBookById(3L);
        });

        assertEquals(ID_NOT_FOUND.getMessage(), exception.getMessage());
        assertEquals(ID_NOT_FOUND.getStatusCode(), exception.getCode());

        verify(bookService, times(1)).getBookById(3L);
    }

    @Test
    void testGetBooksByGenre_Success() {
        when(bookService.getBooksByGenre("Fiction")).thenReturn(Arrays.asList(book1));

        ResponseEntity<List<Book>> response = bookController.getBooksByGenre("Fiction");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Fiction", response.getBody().get(0).getGenre());

        verify(bookService, times(1)).getBooksByGenre("Fiction");
    }

    @Test
    void testGetBooksByGenre_GenreParameterNull() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookController.getBooksByGenre("");
        });

        assertEquals(GENRE_PARAMETER_NULL.getMessage(), exception.getMessage());
        assertEquals(GENRE_PARAMETER_NULL.getStatusCode(), exception.getCode());

        verify(bookService, never()).getBooksByGenre(any());
    }

    @Test
    void testGetBooksByAuthor_Success() {
        when(bookService.getBooksByAuthor("Author One")).thenReturn(Arrays.asList(book1));

        ResponseEntity<List<Book>> response = bookController.getBooksByAuthor("Author One");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Author One", response.getBody().get(0).getAuthor());

        verify(bookService, times(1)).getBooksByAuthor("Author One");
    }

    @Test
    void testGetBooksByAuthor_AuthorParameterNull() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookController.getBooksByAuthor("");
        });

        assertEquals(AUTHOR_PARAMETER_NULL.getMessage(), exception.getMessage());
        assertEquals(AUTHOR_PARAMETER_NULL.getStatusCode(), exception.getCode());

        verify(bookService, never()).getBooksByAuthor(any());
    }
}
