package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.Match;
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findById(Long id);
}