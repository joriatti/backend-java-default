package com.backend.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class AbstractPersistence<T, ID> implements CrudRepository<T, ID> {

    private Class<T> entityClass = (Class) ((ParameterizedType)
            this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext
    protected EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <S extends T> S save(S entity) {
        this.em.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <S extends T> S update(S entity) {
        this.em.merge(entity);
        return entity;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Optional<T> findById(ID id) {
        T entity = this.em.find(this.entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean existsById(ID id) {
        return this.findById(id).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Iterable<T> findAllById(Iterable<ID> iterable) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public long count() {
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(ID id) {
        T entity = this.findById(id).get();
        this.delete(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(T entity) {
        this.em.remove(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAll(Iterable<? extends T> iterable) {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAll() {

    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public T findByIdOrNull(ID id) {
        Optional<T> oEntity = this.findById(id);
        return oEntity.orElse(null);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public T findByUniqueProperty(String field, String value) {
        List<T> result = this.em.createQuery("SELECT t FROM " + this.entityClass.getSimpleName()
                + " t WHERE t." + field.trim() + " = '" + value + "'", this.entityClass).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}