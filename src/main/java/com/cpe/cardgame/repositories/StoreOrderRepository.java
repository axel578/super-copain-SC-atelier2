package com.cpe.cardgame.repositories;

import com.cpe.cardgame.model.StoreOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreOrderRepository extends JpaRepository<StoreOrder, Integer> {
    // Additional custom methods can be defined here if needed
    Page<StoreOrder> findAll(Pageable pageable);

    List<StoreOrder> findAllByUserId(Integer userId);

    Page<StoreOrder> findAllByUserId(Integer userId, Pageable pageable);
}