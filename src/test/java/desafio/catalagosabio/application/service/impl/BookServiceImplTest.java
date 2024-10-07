package desafio.catalagosabio.application.service.impl;

import desafio.catalagosabio.application.dto.BookDto;
import desafio.catalagosabio.domain.exception.NotFoundException;
import desafio.catalagosabio.domain.mapper.BookMapper;
import desafio.catalagosabio.infra.model.Book;
import desafio.catalagosabio.infra.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static desafio.catalagosabio.domain.exception.ExceptionEnum.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;
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

        book2 = Book.builder()
                .id(1L)
                .author("Author Two")
                .genre("Non-Fiction")
                .description("Paperback")
                .title("Book Two")
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
    void testFindAllBooks() {
        List<Book> books = Arrays.asList(book1, book2);
        List<BookDto> bookDtos = Arrays.asList(bookDto1, bookDto2);
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(0, 10), books.size());

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDto(anyList())).thenReturn(bookDtos);

        Page<BookDto> result = bookService.findAllBooks(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals(bookDto1, result.getContent().get(0));
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
        verify(bookMapper, times(1)).toDto(anyList());
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(book1, result.get());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(3L);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(3L);
    }

    @Test
    void testGetBooksByGenre_Success() {
        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findByGenre("Fiction")).thenReturn(books);

        List<Book> result = bookService.getBooksByGenre("Fiction");

        assertEquals(2, result.size());
        assertEquals(book1, result.get(0));
        verify(bookRepository, times(1)).findByGenre("Fiction");
    }

    @Test
    void testGetBooksByGenre_NotFound() {
        when(bookRepository.findByGenre("Unknown")).thenReturn(Arrays.asList());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookService.getBooksByGenre("Unknown");
        });

        assertEquals(NOT_FOUND.getMessage(), exception.getMessage());
        verify(bookRepository, times(1)).findByGenre("Unknown");
    }

    @Test
    void testGetBooksByAuthor_Success() {
        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findByAuthor("Author One")).thenReturn(books);

        List<Book> result = bookService.getBooksByAuthor("Author One");

        assertEquals(2, result.size());
        assertEquals(book1, result.get(0));
        verify(bookRepository, times(1)).findByAuthor("Author One");
    }

    @Test
    void testGetBooksByAuthor_NotFound() {
        when(bookRepository.findByAuthor("Unknown Author")).thenReturn(Arrays.asList());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookService.getBooksByAuthor("Unknown Author");
        });

        assertEquals(NOT_FOUND.getMessage(), exception.getMessage());
        verify(bookRepository, times(1)).findByAuthor("Unknown Author");
    }
}
