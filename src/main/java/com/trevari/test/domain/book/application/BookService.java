package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.exception.NotFoundBookException;
import com.trevari.test.domain.book.adapter.out.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public Book getBook(Long isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundBookException("isbn"));
    }
}
