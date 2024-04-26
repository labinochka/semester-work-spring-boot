package ru.kpfu.itis.beerokspring.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.beerokspring.dto.response.RoleResponse;
import ru.kpfu.itis.beerokspring.model.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toResponse(RoleEntity entity);
}
