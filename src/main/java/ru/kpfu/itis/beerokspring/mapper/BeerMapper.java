package ru.kpfu.itis.beerokspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.response.BeerResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoBeerResponse;
import ru.kpfu.itis.beerokspring.model.BeerEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    @Mapping(target = "uuid", ignore = true)
    BeerEntity toEntity(BeerRequest request);

    BeerResponse toResponse(BeerEntity entity);

    List<ShortInfoBeerResponse> toResponse(List<BeerEntity> entities);
}
