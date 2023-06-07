package com.cpe.cardgame.repositories;

import com.cpe.cardgame.model.Card;
import com.cpe.cardgame.model.PlayParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayPartyRepository extends JpaRepository<PlayParty, Integer> {
    @Query(value = "SELECT * FROM cardschem.play_party WHERE partyCode = :code", nativeQuery = true)
    Optional<PlayParty> findByCode(String code);
}