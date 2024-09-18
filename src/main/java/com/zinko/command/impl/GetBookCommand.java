package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("get")
@RequiredArgsConstructor
public class GetBookCommand implements Command {
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter book id: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            BookDto bookDto = bookService.get(id);
            System.out.printf("%d %s %s %n %s %n", bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getDescription());
        } catch (NumberFormatException e) {
            System.out.println("id must be number");
            handle(scanner);
        }
    }
}
