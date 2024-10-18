package com.zinko.data.repository.impl;

import com.zinko.data.model.Series;
import com.zinko.data.repository.SeriesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeriesRepositoryImpl implements SeriesRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Series save(Series entity) {
        manager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Series> getById(Long key) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Series> query = criteriaBuilder.createQuery(Series.class);
        Root<Series> root = query.from(Series.class);
        CriteriaQuery<Series> criteriaQuery = query.select(root).where(criteriaBuilder.equal(root.get("id"), key));
        Series series = manager.createQuery(criteriaQuery).getSingleResult();
        return Optional.ofNullable(series);
    }

    @Override
    @BatchSize(size = 20)
    public List<Series> getAll() {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Series> query = criteriaBuilder.createQuery(Series.class);
        Root<Series> root = query.from(Series.class);
        return manager.createQuery(query.select(root)).getResultList();
    }

    @Override
    public Series update(Series entity) {
        return manager.merge(entity);
    }

    @Override
    public boolean delete(Long key) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaDelete<Series> criteriaDelete = criteriaBuilder.createCriteriaDelete(Series.class);
        Root<Series> root = criteriaDelete.from(Series.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), key));
        int updated = manager.createQuery(criteriaDelete).executeUpdate();
        return updated == 1;
    }
}
