package com.tomasherrado.sl.controller;

import com.tomasherrado.sl.dto.ProductRequestDTO;
import com.tomasherrado.sl.dto.ProductResponseDTO;
import com.tomasherrado.sl.dto.UpdateQuantityDTO;
import com.tomasherrado.sl.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestion de la lista compartida del supermercado")
public class ProductController {

    private final ProductService service;

    @Operation(summary = "Listar productos", description = "Devuelve todos los productos, o filtra por nombre si se envia el parametro 'search'")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(service.search(search));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Crear producto", description = "Falla con 409 si el producto ya existe")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Editar producto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Actualizar solo la cantidad de un producto")
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductResponseDTO> updateQuantity(@PathVariable Long id, @Valid @RequestBody UpdateQuantityDTO dto) {
        return ResponseEntity.ok(service.updateQuantity(id, dto));
    }

    @Operation(summary = "Marcar/desmarcar producto como comprado")
    @PatchMapping("/{id}/toggle-purchased")
    public ResponseEntity<ProductResponseDTO> togglePurchased(@PathVariable Long id, @RequestParam String userName) {
        return ResponseEntity.ok(service.togglePurchased(id, userName));
    }

    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Vaciar toda la lista")
    @DeleteMapping
    public ResponseEntity<Void> clearAll() {
        service.clearAll();
        return ResponseEntity.noContent().build();
    }
}