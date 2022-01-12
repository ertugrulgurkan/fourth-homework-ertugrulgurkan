package com.ertugrul.spring.service.entityservice;

import com.ertugrul.spring.entity.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public abstract class BaseEntityService<E extends BaseEntity, R extends JpaRepository> {
    private R repository;

    public List<E> findAll() {
        return repository.findAll();
    }

    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    public E save(E e) {
        return (E) repository.save(e);
    }

    public void delete(E e) {
        repository.delete(e);
    }

    public R getRepository() {
        return repository;
    }
}
