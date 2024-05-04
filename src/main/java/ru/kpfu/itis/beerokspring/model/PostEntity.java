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
@Table(name = "post")
public class PostEntity extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Date dateOfPublication;

    @ManyToOne
    @JoinColumn(referencedColumnName = "uuid", name = "author_uuid")
    private AccountEntity author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;
}
