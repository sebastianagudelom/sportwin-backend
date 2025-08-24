package com.sportwin.sportwin_backend.repository;

import com.sportwin.sportwin_backend.entity.Competicion;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CompeticionRepository extends JpaRepository<Competicion, Long> {


    @Query("SELECT c FROM Competicion c JOIN FETCH c.deporte")
    List<Competicion> findAllWithDeporte();

    @Query("SELECT c FROM Competicion c JOIN FETCH c.deporte WHERE c.idCompeticion = :id")
    Optional<Competicion> findByIdWithDeporte(@Param("id") Long id);

}