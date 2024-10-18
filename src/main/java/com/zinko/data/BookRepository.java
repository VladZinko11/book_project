package com.zinko.data;

import com.zinko.data.dao.BookDao;

public interface BookRepository extends CrudRepository<Long, BookDao> {
}
