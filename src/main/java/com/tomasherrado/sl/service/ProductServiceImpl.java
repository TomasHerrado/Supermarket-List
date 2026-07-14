package com.tomasherrado.sl.service;

import com.tomasherrado.sl.dto.ProductRequestDTO;
import com.tomasherrado.sl.dto.ProductResponseDTO;
import com.tomasherrado.sl.dto.UpdateQuantityDTO;
import com.tomasherrado.sl.entity.Product;
import com.tomasherrado.sl.exception.DuplicateProductException;
import com.tomasherrado.sl.exception.ResourceNotFoundException;
import com.tomasherrado.sl.mapper.ProductMapper;
import com.tomasherrado.sl.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> search(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        repository.findByNameIgnoreCase(dto.getName()).ifPresent(p -> {
            throw new DuplicateProductException(
                    "Este producto ya existe en la lista. ¿Deseas modificar la cantidad o crear otro producto?");
        });

        Product product = mapper.toEntity(dto);
        product.setAddedBy(dto.getUserName());
        return mapper.toResponseDTO(repository.save(product));
    }

    @Override
    public ProductResponseDTO updateQuantity(Long id, UpdateQuantityDTO dto) {
        Product product = findEntityById(id);
        product.setQuantity(dto.getQuantity());
        product.setModifiedBy(dto.getUserName());
        return mapper.toResponseDTO(repository.save(product));
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = findEntityById(id);
        mapper.updateEntityFromDTO(dto, product);
        product.setModifiedBy(dto.getUserName());
        return mapper.toResponseDTO(repository.save(product));
    }

    @Override
    public ProductResponseDTO togglePurchased(Long id, String userName) {
        Product product = findEntityById(id);
        product.setPurchased(!Boolean.TRUE.equals(product.getPurchased()));
        product.setModifiedBy(userName);
        return mapper.toResponseDTO(repository.save(product));
    }

    @Override
    public void delete(Long id) {
        Product product = findEntityById(id);
        repository.delete(product);
    }

    @Override
    public void clearAll() {
        repository.deleteAll();
    }

    private Product findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }
}