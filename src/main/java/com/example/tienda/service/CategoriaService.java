package com.example.tienda.service;

import com.example.tienda.dto.CategoriaDTO;
import com.example.tienda.entity.Categoria;
import com.example.tienda.exception.ApiException;
import com.example.tienda.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepo;

    public CategoriaService(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @Transactional
    public Categoria crearCategoria(CategoriaDTO dto) {
        if (categoriaRepo.findByNombre(dto.nombre).isPresent()) {
            throw new ApiException("La categoria con ese nombre ya existe");
        }
        Categoria c = new Categoria();
        c.setNombre(dto.nombre);
        return categoriaRepo.save(c);
    }
}