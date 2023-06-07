package com.cpe.cardgame.repositories;

import com.cpe.cardgame.model.StoreTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreTransactionRepository extends JpaRepository<StoreTransaction, Integer> {
    // Additional custom methods can be defined here if needed
    Page<StoreTransaction> findAll(Pageable pageable);

    List<StoreTransaction> findAllByUserId(Integer userId);

    Page<StoreTransaction> findAllByUserId(Integer userId, Pageable pageable);
}