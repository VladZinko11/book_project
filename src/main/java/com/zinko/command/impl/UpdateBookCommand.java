package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("update")
@RequiredArgsConstructor
public class UpdateBookCommand implements Command {
    public static final String ENTER_BOOK_ID_MESSAGE = "enter.book.id.message";
    public static final String UPDATED_BOOK_MESSAGE = "updated.book.message";
    public static final String ENTER_BOOK_TITLE_MESSAGE = "enter.book.title.message";
    public static final String ENTER_BOOK_AUTHOR_MESSAGE = "enter.book.author.message";
    public static final String ENTER_BOOK_DESCRIPTION_MESSAGE = "enter.book.description.message";
    public static final String NUMBER_FORMAT_EXCEPTION_FOR_ID_MESSAGE = "number.format.exception.for.id.message";
    private final BookService bookService;
    private final CustomMessageSource messageSource;

    @Override
    public void handle(Scanner scanner) {
        System.out.println(messageSource.getMessage(ENTER_BOOK_ID_MESSAGE));
        try {
            Long id = Long.parseLong(scanner.nextLine());

            System.out.println(messageSource.getMessage(ENTER_BOOK_TITLE_MESSAGE));
            String title = scanner.nextLine();
            System.out.println(messageSource.getMessage(ENTER_BOOK_AUTHOR_MESSAGE));
            String author = scanner.nextLine();
            System.out.println(messageSource.getMessage(ENTER_BOOK_DESCRIPTION_MESSAGE));
            String description = scanner.nextLine();
            BookDto bookDto = BookDto.builder()
                    .id(id)
                    .title(title)
                    .author(author)
                    .description(description)
                    .build();
            bookService.update(bookDto);
            BookDto updated = bookService.getById(bookDto.getId());
            System.out.println(messageSource.getMessage(UPDATED_BOOK_MESSAGE, new Object[]{updated.getId(), updated.getAuthor(), updated.getTitle(), updated.getDescription()}));
        } catch (
                NumberFormatException e) {
            System.out.println(messageSource.getMessage(NUMBER_FORMAT_EXCEPTION_FOR_ID_MESSAGE));
            handle(scanner);
        }
    }
}
