package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("update")
@RequiredArgsConstructor
public class UpdateBookCommand implements Command {
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter book id: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());

            System.out.println("Enter book title or empty line: ");
            String title = scanner.nextLine();
            System.out.println("Enter book author or empty line: ");
            String author = scanner.nextLine();
            System.out.println("Enter book description or empty line: ");
            String description = scanner.nextLine();
            BookDto bookDto = BookDto.builder()
                    .id(id)
                    .title(title)
                    .author(author)
                    .description(description)
                    .build();
            bookService.update(bookDto);
            BookDto updated = bookService.get(bookDto.getId());
            System.out.printf("Updated book: %d %s %s %n %s %n", updated.getId(), updated.getAuthor(), updated.getTitle(), updated.getDescription());
        } catch (
                NumberFormatException e) {
            System.out.println("id must be number");
            handle(scanner);
        }
    }
}
