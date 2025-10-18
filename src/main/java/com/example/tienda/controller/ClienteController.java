package com.example.tienda.controller;

import com.example.tienda.dto.ClienteDTO;
import com.example.tienda.entity.Cliente;
import com.example.tienda.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody ClienteDTO dto) {
        Cliente c = clienteService.crearClienteConDireccion(dto);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerCliente(id));
    }
}