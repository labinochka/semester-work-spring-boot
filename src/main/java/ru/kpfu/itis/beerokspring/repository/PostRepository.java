package ru.kpfu.itis.beerokspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.beerokspring.model.PostEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    @Query("select post from PostEntity post where lower(post.title) like :title order by post.title")
    List<PostEntity> findAllByTitle(@Param("title") String title);

    List<PostEntity> findAllByAuthorUuid(UUID uuid);

}
