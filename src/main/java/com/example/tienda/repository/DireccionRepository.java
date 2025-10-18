package com.example.tienda.repository;

import com.example.tienda.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {}