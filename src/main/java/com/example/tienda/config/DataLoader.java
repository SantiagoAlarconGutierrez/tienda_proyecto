package com.example.tienda.config;

import com.example.tienda.entity.Categoria;
import com.example.tienda.entity.Producto;
import com.example.tienda.repository.CategoriaRepository;
import com.example.tienda.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public DataLoader(ProductoRepository productoRepo, CategoriaRepository categoriaRepo){
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria c1 = new Categoria(); c1.setNombre("Electronica");
        Categoria c2 = new Categoria(); c2.setNombre("Hogar");
        categoriaRepo.save(c1); categoriaRepo.save(c2);

        Producto p1 = new Producto(); p1.setNombre("Auriculares"); p1.setPrecio(new BigDecimal("50.00")); p1.setStock(10);
        Producto p2 = new Producto(); p2.setNombre("Tostadora"); p2.setPrecio(new BigDecimal("30.00")); p2.setStock(5);
        p1.getCategorias().add(c1); c1.getProductos().add(p1);
        p2.getCategorias().add(c2); c2.getProductos().add(p2);
        productoRepo.save(p1); productoRepo.save(p2);
    }
}
