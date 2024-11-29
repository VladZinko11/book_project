package com.zinko.servicebook.data.repository;


import com.zinko.servicebook.data.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
