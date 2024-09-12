package com.zinko.command;

import com.zinko.service.CustomMessageSource;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CommandListener {

    public static final String NOT_FOUND_COMMAND_MESSAGE = "not.found.command.message";
    public static final String SERVER_ERROR_MESSAGE = "server.error.message";
    public static final String CHOSE_LANGUAGE_MESSAGE = "Enter ru or en to chose language";
    public static final String COMMAND_MESSAGE = "command.message";
    private final CommandFactory commandFactory;
    private final CustomMessageSource messageSource;


    public void ListenCommand() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(CHOSE_LANGUAGE_MESSAGE);
            String lang = scanner.nextLine();
            if (lang.equals("en")) {
                messageSource.setLocale(new Locale("en"));
                break;
            }
            if (lang.equals("ru")) {
                messageSource.setLocale(new Locale("ru"));
                break;
            }
        }
        System.out.println(messageSource.getMessage(COMMAND_MESSAGE));
        while (true) {
            String command = scanner.nextLine();
            try {
                Command commandInstance = commandFactory.getCommand(command);
                if (commandInstance == null) {
                    System.out.println(messageSource.getMessage(NOT_FOUND_COMMAND_MESSAGE));
                    continue;
                }
                commandInstance.handle(scanner);
            } catch (NotFoundException | ServerException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(messageSource.getMessage(SERVER_ERROR_MESSAGE));
            }
        }
    }
}
