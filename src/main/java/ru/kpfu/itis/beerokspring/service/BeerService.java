package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.response.BeerResponse;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<BeerResponse> getBeersBySort(String sort);

    BeerResponse getById(UUID uuid);

    BeerResponse getByType(String type);

    void add(BeerRequest beer);

    void updateById(BeerRequest newBeer, UUID uuid);

    void deleteById(UUID uuid);
}
