package ru.kpfu.itis.beerokspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String avatar = "https://mirtex.ru/wp-content/uploads/2023/04/unnamed.jpg";

    @Column(nullable = false)
    private String about = "-";

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "author")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "author")
    private List<CommentEntity> comments;
}
