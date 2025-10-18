package com.example.tienda.controller;

import com.example.tienda.dto.CategoriaDTO;
import com.example.tienda.entity.Categoria;
import com.example.tienda.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) { this.categoriaService = categoriaService; }

    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.crearCategoria(dto));
    }
}