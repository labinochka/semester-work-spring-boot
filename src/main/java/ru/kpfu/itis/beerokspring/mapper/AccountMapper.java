package ru.kpfu.itis.beerokspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.beerokspring.dto.request.AccountRegistrationRequest;
import ru.kpfu.itis.beerokspring.dto.response.AccountResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;
import ru.kpfu.itis.beerokspring.model.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "uuid", ignore = true)
    AccountEntity toEntity(AccountRegistrationRequest request);

    ShortInfoAccountResponse toShortResponse(AccountEntity entity);

    AccountResponse toResponse(AccountEntity entity);
}
