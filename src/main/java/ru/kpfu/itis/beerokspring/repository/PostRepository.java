package ru.kpfu.itis.beerokspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import ru.kpfu.itis.beerokspring.model.PostEntity;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    @Query("select post from PostEntity post where lower(post.title) like :title order by post.title")
    List<PostEntity> findAllByTitle(@Param("title") String title);

    List<PostEntity> findAllByAuthorUuid(UUID uuid);

    @Modifying
    @Query("update PostEntity post set post.title = ?1, post.content = ?2, post.image = ?3 " +
            "where post.uuid = ?4")
    void updateByUuid(String title, String content, String image, UUID uuid);
}
