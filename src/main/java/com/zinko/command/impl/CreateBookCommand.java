package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("create")
@RequiredArgsConstructor
public class CreateBookCommand implements Command {
    private final BookService bookService;
    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();
        System.out.println("Enter book description: ");
        String description = scanner.nextLine();
        BookDto bookDto = bookService.create(BookDto.builder()
                .title(title)
                .author(author)
                .description(description)
                .build());
        System.out.printf("Created book: %d %s %s %n%s%n", bookDto.getId(), bookDto.getAuthor(), bookDto.getTitle(), bookDto.getDescription());
    }
}
