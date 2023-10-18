package bookstore;

import bookstore.model.Book;
import bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookstoreApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book1 = new Book();
                book1.setTitle("book1");
                book1.setAuthor("A B");
                book1.setIsbn("abc");
                book1.setPrice(BigDecimal.valueOf(300));
                book1.setDescription("good book");
                book1.setCoverImage("hors");
                bookService.save(book1);
                Book book2 = new Book();
                book2.setTitle("book2");
                book2.setAuthor("С D");
                book2.setIsbn("bcd");
                book2.setPrice(BigDecimal.valueOf(400));
                book2.setDescription("good book");
                book2.setCoverImage("cat");
                bookService.save(book2);
                System.out.println(bookService.findAll());
            }
        };
    }
}
