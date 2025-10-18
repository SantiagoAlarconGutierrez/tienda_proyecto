package com.example.tienda.service;

import com.example.tienda.dto.ItemPedidoDTO;
import com.example.tienda.dto.PedidoDTO;
import com.example.tienda.entity.ItemPedido;
import com.example.tienda.entity.Pedido;
import com.example.tienda.entity.Producto;
import com.example.tienda.entity.enums.PedidoEstado;
import com.example.tienda.exception.ApiException;
import com.example.tienda.exception.ResourceNotFoundException;
import com.example.tienda.repository.ClienteRepository;
import com.example.tienda.repository.PedidoRepository;
import com.example.tienda.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public PedidoService(PedidoRepository pedidoRepo, ClienteRepository clienteRepo, ProductoRepository productoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.productoRepo = productoRepo;
    }

    @Transactional
    public Pedido crearPedido(PedidoDTO dto) {
        var cliente = clienteRepo.findById(dto.clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Pedido p = new Pedido();
        p.setCliente(cliente);
        p.setFecha(LocalDateTime.now());

        Set<Long> ids = new HashSet<>();
        for (ItemPedidoDTO it : dto.items) {
            if (!ids.add(it.productoId)) {
                throw new ApiException("Producto repetido en el pedido: " + it.productoId);
            }
            Producto prod = productoRepo.findById(it.productoId).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + it.productoId));
            if (prod.getStock() < it.cantidad) {
                throw new ApiException("Stock insuficiente para producto: " + prod.getNombre());
            }
            prod.setStock(prod.getStock() - it.cantidad);

            ItemPedido ip = new ItemPedido();
            ip.setProducto(prod);
            ip.setCantidad(it.cantidad);
            ip.setPrecioUnitario(prod.getPrecio());
            p.addItem(ip);
        }

        BigDecimal total = p.getItems().stream()
                .map(i -> i.getPrecioUnitario().multiply(new BigDecimal(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        p.setTotal(total);
        p.setEstado(PedidoEstado.PENDIENTE);

        return pedidoRepo.save(p);
    }

    @Transactional
    public Pedido cambiarEstado(Long pedidoId, PedidoEstado nuevoEstado) {
        Pedido p = pedidoRepo.findById(pedidoId).orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        if (p.getEstado() == PedidoEstado.CANCELADO) {
            throw new ApiException("No se puede cambiar el estado de un pedido cancelado");
        }
        if (p.getEstado() == PedidoEstado.ENTREGADO) {
            throw new ApiException("Pedido ya entregado");
        }
        if (nuevoEstado == PedidoEstado.ENVIADO && p.getEstado() != PedidoEstado.PAGADO) {
            throw new ApiException("Solo se puede enviar un pedido que esté PAGADO");
        }
        p.setEstado(nuevoEstado);
        return pedidoRepo.save(p);
    }
}