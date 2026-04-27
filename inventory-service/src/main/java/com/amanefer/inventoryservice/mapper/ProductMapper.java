package com.amanefer.inventoryservice.mapper;

import com.amanefer.inventoryservice.model.dto.CreateProductRequest;
import com.amanefer.inventoryservice.model.dto.ProductResponseDto;
import com.amanefer.inventoryservice.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(CreateProductRequest request);

    ProductResponseDto toProductResponseDto(Product entity);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> entity);
}
