package astue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.entity.Book;
import astue.service.BookService;
import astue.util.CustomResponse;

@RestController
@RequestMapping("/api")
public class BookController {
	@Autowired
	private final BookService bookService;
	
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	@GetMapping("/books")
	public CustomResponse<Book> getAll(){
		return bookService.getAll(); 
	}
	@GetMapping("/book/{title}")
	public CustomResponse<Book> getBookByTitle(@PathVariable("title") String title){
		return  bookService.findBookByTitle(title); 
	}
	@PostMapping("/books")
	public  CustomResponse<Book> addBook(Book book){
		return bookService.addBook(book); 
	}

}
