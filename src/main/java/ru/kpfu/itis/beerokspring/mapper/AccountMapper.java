package ru.kpfu.itis.beerokspring.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;
import ru.kpfu.itis.beerokspring.model.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    ShortInfoAccountResponse toResponse(AccountEntity entity);
}
