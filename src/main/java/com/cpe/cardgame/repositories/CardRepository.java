package com.cpe.cardgame.repositories;

import com.cpe.cardgame.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    // Additional custom methods can be defined here if needed
    Page<Card> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM cardschem.card WHERE user_id = :userId", nativeQuery = true)
    List<Card> findAllByUserId(Integer userId);

    Page<Card> findAllByUserId(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM cardschem.card WHERE to_sell = TRUE", nativeQuery = true)
    List<Card> findAllByUserIdZero();

    @Query(value = "SELECT * FROM cardschem.card WHERE user_id = :userId AND hp > 0", nativeQuery = true)
    List<Card> findAllByUserIdCardAvailable(Integer userId);

    @Query(value = "SELECT * FROM cardschem.card WHERE user_id = :user_id AND to_sell = FALSE", nativeQuery = true)
    List<Card> findAllByUserId(int user_id);
}