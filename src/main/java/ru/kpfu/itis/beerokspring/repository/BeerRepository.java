package ru.kpfu.itis.beerokspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.beerokspring.model.BeerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<BeerEntity, UUID> {

    List<BeerEntity> findAllBySort(String sort);

    Optional<BeerEntity> findByType(String type);

    @Modifying
    @Query("update BeerEntity beer set beer.sort = ?1, beer.type = ?2, beer.content = ?3, beer.image = ?4 " +
            "where beer.uuid = ?5")
    void updateByUuid(String sort, String type, String content, String image, UUID uuid);
}
