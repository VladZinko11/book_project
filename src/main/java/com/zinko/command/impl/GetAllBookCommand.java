package com.zinko.command.impl;

import com.zinko.command.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component("get all")
@RequiredArgsConstructor
public class GetAllBookCommand implements Command {
    private final BookService bookService;

    @Override
    public void handle(Scanner scanner) {
        List<BookDto> list = bookService.getAll();
        if (list.isEmpty()) {
            System.out.println("No books found");
        } else {
            for (BookDto book :
                    list) {
                System.out.printf("%d %s %s%n", book.getId(), book.getTitle(), book.getAuthor());
            }
        }
    }
}
