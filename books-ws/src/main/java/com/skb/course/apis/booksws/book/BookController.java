package com.skb.course.apis.booksws.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/books")
public class BookController {

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {

        book.setBookId(UUID.randomUUID().toString());

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable String bookId) {

        Book book = new Book(bookId, UUID.randomUUID().toString(), "API Security",
                "SKB Publishers", "01-02-2010");

        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
