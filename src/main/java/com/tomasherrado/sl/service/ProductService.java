package com.tomasherrado.sl.service;

import com.tomasherrado.sl.dto.ProductRequestDTO;
import com.tomasherrado.sl.dto.ProductResponseDTO;
import com.tomasherrado.sl.dto.UpdateQuantityDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAll();
    List<ProductResponseDTO> search(String name);
    ProductResponseDTO create(ProductRequestDTO dto);
    ProductResponseDTO updateQuantity(Long id, UpdateQuantityDTO dto);
    ProductResponseDTO update(Long id, ProductRequestDTO dto);
    ProductResponseDTO togglePurchased(Long id, String userName);
    void delete(Long id);
    void clearAll();
}