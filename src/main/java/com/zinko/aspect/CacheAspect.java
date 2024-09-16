package com.zinko.aspect;

import com.zinko.data.cache.MyCache;
import com.zinko.model.Book;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {
    private final MyCache<Long, Book> cache;

    @Pointcut("execution(public * com.zinko.data.impl.BookRepositoryImpl.getById(*))")
    private void getByIdMethodFromBookRepository() {
    }

    @Around(value = "getByIdMethodFromBookRepository()")
    public Optional<Book> getById(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        if(!cache.searchCache(id)) {
            Optional<Book> optionalBook =(Optional<Book>) joinPoint.proceed();
            optionalBook.ifPresent(book->cache.addToCache(id, optionalBook.get()));
            return optionalBook;
        }
            return cache.getFromCache(id);
    }





}
