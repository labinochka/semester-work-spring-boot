package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.response.BeerResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoBeerResponse;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.BeerMapper;
import ru.kpfu.itis.beerokspring.repository.BeerRepository;
import ru.kpfu.itis.beerokspring.service.BeerService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository repository;

    private final BeerMapper mapper;

    @Override
    public List<ShortInfoBeerResponse> getBeersBySort(String sort) {
        return mapper.toResponse(repository.findAllBySort(sort));
    }

    @Override
    public BeerResponse getById(UUID uuid) {
        return mapper.toResponse(repository.findById(uuid)
                .orElseThrow(PostNotFoundException::new));
    }

    @Override
    public BeerResponse getByType(String type) {
        return mapper.toResponse(repository.findByType(type)
                .orElseThrow(PostNotFoundException::new));
    }

    @Override
    public void add(BeerRequest beer) {
        repository.save(mapper.toEntity(beer));
    }


    @Override
    public void updateById(BeerRequest newBeer, UUID uuid) {
        getById(uuid);
        repository.updateByUuid(newBeer.sort(), newBeer.type(), newBeer.content(), newBeer.image(), uuid);
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(repository.findById(uuid)
                .orElseThrow(PostNotFoundException::new).getUuid());
    }
}
