package ru.kpfu.itis.beerokspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.beerokspring.model.BeerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<BeerEntity, UUID> {

    List<BeerEntity> findAllBySort(String sort);

    Optional<BeerEntity> findByType(String type);

}
