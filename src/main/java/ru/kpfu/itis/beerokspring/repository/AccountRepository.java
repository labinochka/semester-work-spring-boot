package ru.kpfu.itis.beerokspring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.beerokspring.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByEmail(String email);

    @Query("select account FROM AccountEntity account where account.role.name = :roleName " +
            "and account.username not in (SELECT a.username from AccountEntity a where a.username = :username)")
    List<AccountEntity> findByRoleName(@Param("roleName") String roleName, @Param("username") String username);

}
