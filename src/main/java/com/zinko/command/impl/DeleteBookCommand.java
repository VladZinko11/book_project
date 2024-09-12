package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("delete")
@RequiredArgsConstructor
public class DeleteBookCommand implements Command {
    public static final String ENTER_BOOK_ID_MESSAGE = "enter.book.id.message";
    public static final String BOOK_WITH_ID_DELETED_MESSAGE = "book.with.id.deleted.message";
    public static final String NUMBER_FORMAT_EXCEPTION_FOR_ID_MESSAGE = "number.format.exception.for.id.message";
    private final CustomMessageSource messageSource;
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println(messageSource.getMessage(ENTER_BOOK_ID_MESSAGE));
        try {
            Long id = Long.parseLong(scanner.nextLine());
            bookService.delete(id);
            System.out.println(messageSource.getMessage(BOOK_WITH_ID_DELETED_MESSAGE, new Object[]{id}));
        } catch (NumberFormatException e) {
            System.out.println(messageSource.getMessage(NUMBER_FORMAT_EXCEPTION_FOR_ID_MESSAGE));
            handle(scanner);
        }
    }
}
