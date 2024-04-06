package ru.kpfu.itis.beerokspring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "beer")
public class BeerEntity extends AbstractEntity {

    @Column(nullable = false)
    private String sort;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String content;

}
