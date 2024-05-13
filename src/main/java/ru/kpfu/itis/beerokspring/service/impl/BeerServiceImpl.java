package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.response.BeerResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoBeerResponse;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.BeerMapper;
import ru.kpfu.itis.beerokspring.model.BeerEntity;
import ru.kpfu.itis.beerokspring.repository.BeerRepository;
import ru.kpfu.itis.beerokspring.service.BeerService;
import ru.kpfu.itis.beerokspring.util.FileUploaderUtil;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerServiceImpl implements BeerService {

    @Value("${upload.url.suffix.beers}")
    private String urlBeers;

    private final BeerRepository repository;

    private final BeerMapper mapper;

    @Override
    public List<ShortInfoBeerResponse> getBeersBySort(String sort) {
        return mapper.toResponse(repository.findAllBySort(sort));
    }

    @Override
    public BeerResponse getById(UUID uuid) {
        try {
            return mapper.toResponse(repository.findById(uuid)
                    .orElseThrow(PostNotFoundException::new));
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        }

    }

    @Override
    public BeerResponse getByType(String type) {
        try {
            return mapper.toResponse(repository.findByType(type)
                    .orElseThrow(PostNotFoundException::new));
        } catch (PostNotFoundException e) {
            log.error("Post not found for type: {}", type, e);
            throw e;
        }

    }

    @Override
    public void add(BeerRequest beer, String sort) {
        BeerEntity entity = mapper.toEntity(beer);
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString();
        entity.setUuid(uuid);
        entity.setSort(sort);
        entity.setImage(FileUploaderUtil.uploadFile(beer.image(), fileName, urlBeers));
        repository.save(mapper.toEntity(beer));
    }


    @Override
    public void updateById(BeerRequest newBeer, UUID uuid) {
        getById(uuid);
    }

    @Override
    public void deleteById(UUID uuid) {
        try {
            repository.deleteById(repository.findById(uuid)
                    .orElseThrow(PostNotFoundException::new).getUuid());
        } catch (PostNotFoundException e) {
            log.error("Post not found for id: {}", uuid, e);
            throw e;
        }
    }
}
