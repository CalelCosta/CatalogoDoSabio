package desafio.catalagosabio.infra.repository;

import desafio.catalagosabio.infra.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void deveBuscarTodosOsLivros() {
        List<Book> books = bookRepository.findAll();

        assertThat(books).isNotEmpty();
    }

    @Test
    void deveBuscarPorGenero(){
        List<Book> byGenre = bookRepository.findByGenre("Non-fiction");

        assertThat(byGenre).isNotEmpty();
    }

    @Test
    void deveBuscarPorAutor(){
        List<Book> byGenre = bookRepository.findByAuthor("Lisa");

        assertThat(byGenre).isNotEmpty();
    }
}
