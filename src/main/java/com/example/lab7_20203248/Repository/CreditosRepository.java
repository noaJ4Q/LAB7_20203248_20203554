package com.example.lab7_20203248.Repository;

import com.example.lab7_20203248.Entity.Creditos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditosRepository extends JpaRepository<Creditos, Integer> {
}
