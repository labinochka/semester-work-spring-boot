package ru.kpfu.itis.beerokspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity extends AbstractEntity {

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date dateOfPublication;

    @ManyToOne
    @JoinColumn(referencedColumnName = "uuid", name = "author_uuid")
    private AccountEntity author;

    @ManyToOne
    @JoinColumn(referencedColumnName = "uuid", name = "post_uuid")
    private PostEntity post;
}
