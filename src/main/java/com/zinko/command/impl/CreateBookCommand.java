package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("create")
@RequiredArgsConstructor
public class CreateBookCommand implements Command {
    public static final String ENTER_BOOK_TITLE_MESSAGE = "enter.book.title.message";
    public static final String ENTER_BOOK_AUTHOR_MESSAGE = "enter.book.author.message";
    public static final String ENTER_BOOK_DESCRIPTION_MESSAGE = "enter.book.description.message";
    public static final String CREATED_BOOK_MESSAGE = "created.book.message";
    private final CustomMessageSource messageSource;
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println(messageSource.getMessage(ENTER_BOOK_TITLE_MESSAGE));
        String title = scanner.nextLine();
        System.out.println(messageSource.getMessage(ENTER_BOOK_AUTHOR_MESSAGE));
        String author = scanner.nextLine();
        System.out.println(messageSource.getMessage(ENTER_BOOK_DESCRIPTION_MESSAGE));
        String description = scanner.nextLine();
        BookDto bookDto = bookService.create(BookDto.builder()
                .title(title)
                .author(author)
                .description(description)
                .build());
        System.out.println(messageSource.getMessage(CREATED_BOOK_MESSAGE, new Object[]{bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getDescription()}));
    }
}
