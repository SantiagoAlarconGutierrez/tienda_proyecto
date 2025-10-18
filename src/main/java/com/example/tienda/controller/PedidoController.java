package com.example.tienda.controller;

import com.example.tienda.dto.PedidoDTO;
import com.example.tienda.entity.Pedido;
import com.example.tienda.entity.enums.PedidoEstado;
import com.example.tienda.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) { this.pedidoService = pedidoService; }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody PedidoDTO dto) {
        return ResponseEntity.ok(pedidoService.crearPedido(dto));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam PedidoEstado estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }
}