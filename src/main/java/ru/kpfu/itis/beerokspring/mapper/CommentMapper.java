package ru.kpfu.itis.beerokspring.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.beerokspring.dto.response.CommentResponse;
import ru.kpfu.itis.beerokspring.model.CommentEntity;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface CommentMapper {

    CommentResponse toResponse(CommentEntity entity);

}
