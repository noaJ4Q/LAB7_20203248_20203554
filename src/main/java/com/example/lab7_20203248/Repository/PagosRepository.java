package com.example.lab7_20203248.Repository;

import com.example.lab7_20203248.Entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {
}
