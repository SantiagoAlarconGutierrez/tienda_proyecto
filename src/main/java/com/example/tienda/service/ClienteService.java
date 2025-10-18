package com.example.tienda.service;

import com.example.tienda.dto.ClienteDTO;
import com.example.tienda.dto.DireccionDTO;
import com.example.tienda.entity.Cliente;
import com.example.tienda.entity.Direccion;
import com.example.tienda.exception.ResourceNotFoundException;
import com.example.tienda.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @Transactional
    public Cliente crearClienteConDireccion(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.nombre);
        c.setEmail(dto.email);
        if (dto.direccion != null) {
            Direccion d = new Direccion();
            d.setCalle(dto.direccion.calle);
            d.setCiudad(dto.direccion.ciudad);
            d.setPais(dto.direccion.pais);
            d.setZip(dto.direccion.zip);
            c.setDireccion(d);
        }
        return clienteRepo.save(c);
    }

    @Transactional(readOnly = true)
    public ClienteDTO obtenerCliente(Long id) {
        Cliente c = clienteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        ClienteDTO dto = new ClienteDTO();
        dto.id = c.getId();
        dto.nombre = c.getNombre();
        dto.email = c.getEmail();
        if (c.getDireccion() != null) {
            DireccionDTO dd = new DireccionDTO();
            dd.id = c.getDireccion().getId();
            dd.calle = c.getDireccion().getCalle();
            dd.ciudad = c.getDireccion().getCiudad();
            dd.pais = c.getDireccion().getPais();
            dd.zip = c.getDireccion().getZip();
            dto.direccion = dd;
        }
        return dto;
    }
}