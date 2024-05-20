package ru.kpfu.itis.beerokspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.beerokspring.dto.request.BeerRequest;
import ru.kpfu.itis.beerokspring.dto.request.BeerUpdateRequest;
import ru.kpfu.itis.beerokspring.dto.response.BeerResponse;
import ru.kpfu.itis.beerokspring.dto.response.ShortInfoBeerResponse;
import ru.kpfu.itis.beerokspring.exception.BeerNotFoundException;
import ru.kpfu.itis.beerokspring.exception.PostNotFoundException;
import ru.kpfu.itis.beerokspring.mapper.BeerMapper;
import ru.kpfu.itis.beerokspring.model.BeerEntity;
import ru.kpfu.itis.beerokspring.repository.BeerRepository;
import ru.kpfu.itis.beerokspring.service.BeerService;
import ru.kpfu.itis.beerokspring.util.FileUploaderUtil;
import ru.kpfu.itis.beerokspring.util.RefactoringString;

import java.util.List;
import java.util.Objects;
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
    public BeerResponse getById(UUID id) {
        try {
            return mapper.toResponse(repository.findById(id)
                    .orElseThrow(PostNotFoundException::new));
        } catch (PostNotFoundException e) {
            log.error("Beer not found for id: {}", id, e);
            throw e;
        }

    }

    @Override
    public BeerResponse getByType(String type) {
        try {
            return mapper.toResponse(repository.findByType(type)
                    .orElseThrow(PostNotFoundException::new));
        } catch (BeerNotFoundException e) {
            log.error("Beer not found for type: {}", type, e);
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
        repository.save(entity);
    }


    @Override
    public void updateById(BeerUpdateRequest newBeer, UUID id) {
        try {
            BeerEntity beer = repository.findById(id).orElseThrow(BeerNotFoundException::new);
            beer.setSort(RefactoringString.capitalizeFirstLetter(newBeer.sort()));
            beer.setType(RefactoringString.capitalizeFirstLetter(newBeer.type()));
            beer.setContent(newBeer.content());
            if (!Objects.requireNonNull(newBeer.image().getOriginalFilename()).isEmpty()) {
                String fileName = id.toString();
                beer.setImage(FileUploaderUtil.uploadFile(newBeer.image(), fileName, urlBeers));
            }
            repository.save(beer);
        } catch (BeerNotFoundException e) {
            log.error("Beer not found for id: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            BeerEntity beer = repository.findById(id)
                    .orElseThrow(BeerNotFoundException::new);
            repository.delete(beer);
        } catch (BeerNotFoundException e) {
            log.error("Beer not found for id: {}", id, e);
            throw e;
        }
    }

    @Override
    public String validate(UUID id, BeerUpdateRequest request) {
        try {
            BeerEntity beer = repository.findById(id).orElseThrow(PostNotFoundException::new);
            if (!beer.getType().equals(request.type()) && repository.findByType(request.type()).isPresent()) {
                return "Такой тип есть";
            }
            if (!request.sort().equalsIgnoreCase("Эль") &&
                    !request.sort().equalsIgnoreCase("Лагер") &&
                    !request.sort().equalsIgnoreCase("Смешанное")) {
                return "Такого сорта нет. Выберите эль, лагер или смешанное";
            }
        } catch (BeerNotFoundException e) {
            log.error("Beer not found for id: {}", id, e);
            throw e;
        }
        return null;
    }
}
