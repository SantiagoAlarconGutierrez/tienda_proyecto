package com.example.tienda.dto;

import java.math.BigDecimal;
import java.util.Set;

public class ProductoDTO {
    public Long id;
    public String nombre;
    public BigDecimal precio;
    public Integer stock;
    public Set<String> categorias;
}