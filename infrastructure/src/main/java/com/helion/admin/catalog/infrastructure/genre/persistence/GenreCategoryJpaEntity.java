package com.helion.admin.catalog.infrastructure.genre.persistence;

import com.helion.admin.catalog.domain.category.CategoryID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="genres_categories")
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryID id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity () {}

    private GenreCategoryJpaEntity(final GenreJpaEntity aGenre, final CategoryID aCategoryID){
        this.id = GenreCategoryID.from(aGenre.getId(), aCategoryID.getValue());
        this.genre = aGenre;
    }

    public static GenreCategoryJpaEntity from(final GenreJpaEntity aGenre, final CategoryID aCategoryID){
        return new GenreCategoryJpaEntity(aGenre, aCategoryID);
    }

    public GenreCategoryID getId() {
        return id;
    }

    public void setId(GenreCategoryID id) {
        this.id = id;
    }

    public GenreJpaEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreJpaEntity genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreCategoryJpaEntity that = (GenreCategoryJpaEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
