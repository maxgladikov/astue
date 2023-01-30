package astue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import astue.entity.Book;
import astue.entity.Test;
import astue.repository.BookRepository;

@SpringBootApplication
public class AstueApplication implements CommandLineRunner{
	@Autowired
	BookRepository bookRepository;
		//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
	public static void main(String[] args) {
		SpringApplication.run(AstueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Book book1=new Book("Война и мир","Л.Н. Толстой");
//		Book book2=new Book("Капитанская дочка","А.С. Пушкин");
//		bookRepository.save(book1);
//		bookRepository.save(book2);



	}

}
