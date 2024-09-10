package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("delete")
@RequiredArgsConstructor
public class DeleteBookCommand implements Command {
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Enter book id: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            bookService.delete(id);
            System.out.println("book with id " + id + " deleted");
        } catch (NumberFormatException e) {
            System.out.println("id must be number");
            handle(scanner);
        }
    }
}
