package com.zinko.servicestatistic.service;

import com.zinko.servicestatistic.event.RequestItem;
import com.zinko.servicestatistic.repository.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {
    private final Repository repository;


    @KafkaListener(topics = "statistic")
    public void listen(RequestItem requestItem) {
        log.info("got message from statistic: {}", requestItem.getUri());
        repository.save(requestItem);
    }
}
