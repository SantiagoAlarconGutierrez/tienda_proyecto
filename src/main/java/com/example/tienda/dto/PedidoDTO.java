package com.example.tienda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    public Long id;
    public LocalDateTime fecha;
    public String estado;
    public BigDecimal total;
    public Long clienteId;
    public List<ItemPedidoDTO> items;
}