package com.zinko.servicemedia.aspect;

import com.zinko.servicestatistic.event.RequestItem;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestMessageAspect {
    private final KafkaTemplate<String, RequestItem> kafkaTemplate;

    @Pointcut("execution(public * com.zinko.servicemedia.web.controller..*(..))")
    private void publicMethodsFromController() {
    }


    @Before(value = "publicMethodsFromController()")
    public void logBefore() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
        String requestURI = request.getRequestURI();
        RequestItem requestItem = new RequestItem();
        requestItem.setUri(requestURI);
        kafkaTemplate.send("statistic", requestItem);
        log.info("send message in statistic: {}", requestItem.getUri());
    }
}
