package com.zinko.data;

import com.zinko.model.Book;

import java.util.List;

public interface IdGenerator {
    Long generateBookId(List<Book> books);
}
