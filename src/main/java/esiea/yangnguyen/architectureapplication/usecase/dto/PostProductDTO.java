package esiea.yangnguyen.architectureapplication.usecase.dto;

import esiea.yangnguyen.architectureapplication.domain.entities.State;
import esiea.yangnguyen.architectureapplication.domain.entities.ProductStatus;

public record PostProductDTO(Long id, String name, String description, String brand, State state, String size,
                             String category, String season, long providerId, ProductStatus status) { }
