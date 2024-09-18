package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("get")
@RequiredArgsConstructor
public class GetBookCommand implements Command {
    public static final String ENTER_BOOK_ID_MESSAGE = "enter.book.id.message";
    public static final String BOOK_MESSAGE = "book.message";
    public static final String NUMBER_FORMAT_EXCEPTION_FOR_ID_MESSAGE = "number.format.exception.for.id.message";
    private final BookService bookService;
    private final CustomMessageSource messageSource;

    @Override
    public void handle(Scanner scanner) {
        System.out.println(messageSource.getMessage(ENTER_BOOK_ID_MESSAGE));
        try {
            Long id = Long.parseLong(scanner.nextLine());
            BookDto bookDto = bookService.get(id);
            System.out.printf(messageSource.getMessage(BOOK_MESSAGE, new Object[]{bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getDescription()}));
        } catch (NumberFormatException e) {
            System.out.println(messageSource.getMessage(NUMBER_FORMAT_EXCEPTION_FOR_ID_MESSAGE));
            handle(scanner);
        }
    }
}
