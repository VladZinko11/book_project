package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component("get all")
@RequiredArgsConstructor
public class GetAllBookCommand implements Command {
    public static final String NOT_BOOKS_FOUND_MESSAGE = "not.found.books.message";
    private static final String BOOKS_MESSAGE = "books.message";
    private final CustomMessageSource messageSource;
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        List<BookDto> list = bookService.getAll();
        if (list.isEmpty()) {
            System.out.println(messageSource.getMessage(NOT_BOOKS_FOUND_MESSAGE));
        } else {
            for (BookDto book :
                    list) {
                System.out.println(messageSource.getMessage(BOOKS_MESSAGE, new Object[]{book.getId(), book.getTitle(), book.getAuthor()}));
            }
        }
    }
}
