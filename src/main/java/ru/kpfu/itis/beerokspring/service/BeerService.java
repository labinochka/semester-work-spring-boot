package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.request.BeerUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.BeerResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoBeerResponse;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<ShortInfoBeerResponse> getBeersBySort(String sort);

    BeerResponse getById(UUID id);

    BeerResponse getByType(String type);

    void add(BeerRequest beer, String sort);

    void updateById(BeerUpdateRequest newBeer, UUID id);

    void deleteById(UUID uuid);

    String validate(UUID id, BeerUpdateRequest request);
}
