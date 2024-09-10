package com.zinko.command;

import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CommandListener {
    private final CommandFactory commandFactory;

    public void ListenCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Enter command:
                create - to add book
                get - to get book by id
                get all - to get all books
                delete - to delete book by id
                update - to update book
                exit - to exit
                """);

        while (true) {
            String command = scanner.nextLine();
            try {
                Command commandInstance = commandFactory.getCommand(command);
                if(commandInstance==null) {
                    System.out.println("Not found command " + command);
                    continue;
                }
                commandInstance.handle(scanner);
            }
            catch (NotFoundException | ServerException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Oops, something wrong");
            }
        }
    }
}
