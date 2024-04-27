package ru.kpfu.itis.beerokspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.beerokspring.dto.request.PostRequest;
import ru.kpfu.itis.beerokspring.dto.response.PostResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoPostResponse;
import ru.kpfu.itis.beerokspring.model.PostEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface PostMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "image", ignore = true)
    PostEntity toEntity(PostRequest request);

    PostResponse toResponse(PostEntity entity);

    List<ShortInfoPostResponse> toResponse(List<PostEntity> entities);
}
