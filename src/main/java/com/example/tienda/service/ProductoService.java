package com.example.tienda.service;

import com.example.tienda.dto.ProductoDTO;
import com.example.tienda.entity.Categoria;
import com.example.tienda.entity.Producto;
import com.example.tienda.exception.ResourceNotFoundException;
import com.example.tienda.repository.CategoriaRepository;
import com.example.tienda.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoService(ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @Transactional
    public Producto crearProducto(ProductoDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.nombre);
        p.setPrecio(dto.precio);
        p.setStock(dto.stock);
        if (dto.categorias != null) {
            for (String nombre : dto.categorias) {
                Categoria c = categoriaRepo.findByNombre(nombre).orElse(null);
                if (c != null) {
                    p.getCategorias().add(c);
                    c.getProductos().add(p);
                }
            }
        }
        return productoRepo.save(p);
    }

    @Transactional(readOnly = true)
    public Set<ProductoDTO> listarPorCategoria(Long categoriaId) {
        Categoria c = categoriaRepo.findById(categoriaId).orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
        return c.getProductos().stream().map(prod -> {
            ProductoDTO dto = new ProductoDTO();
            dto.id = prod.getId(); dto.nombre = prod.getNombre(); dto.precio = prod.getPrecio(); dto.stock = prod.getStock();
            dto.categorias = prod.getCategorias().stream().map(Categoria::getNombre).collect(Collectors.toSet());
            return dto;
        }).collect(Collectors.toSet());
    }
}