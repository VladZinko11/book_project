package com.zinko.servicestatistic.repository;

import com.zinko.servicestatistic.event.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Repository extends JpaRepository<RequestItem, Long> {

}
