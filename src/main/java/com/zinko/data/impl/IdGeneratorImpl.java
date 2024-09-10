package com.zinko.data.impl;

import com.zinko.data.IdGenerator;
import com.zinko.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IdGeneratorImpl implements IdGenerator {
    @Override
    public Long generateBookId(List<Book> books) {
        int lastIndex = books.size() - 1;
        if (lastIndex < 0) {
            return 1L;
        } else {
            return books.get(lastIndex).getId() + 1;
        }
    }
}
