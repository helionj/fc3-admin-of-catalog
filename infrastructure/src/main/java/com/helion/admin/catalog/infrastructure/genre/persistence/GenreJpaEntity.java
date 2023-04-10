package com.helion.admin.catalog.infrastructure.genre.persistence;

import com.helion.admin.catalog.domain.category.CategoryID;
import com.helion.admin.catalog.domain.genre.Genre;
import com.helion.admin.catalog.domain.genre.GenreID;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="Genre")
@Table(name="genres")
public class GenreJpaEntity {
    @Id
    @Column(name="id", nullable = false)
    private String id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GenreCategoryJpaEntity> categories;

    @Column(name="created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name="updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name="deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public GenreJpaEntity(){}

    public GenreJpaEntity(final String anId,
                          final String aName,
                          final boolean isActive,
                          final Instant createdAt,
                          final Instant updatedAt,
                          final Instant deletedAt) {
        this.id = anId;
        this.name = aName;
        this.active = isActive;
        this.categories = new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static GenreJpaEntity from(final Genre aGenre){
        final var anEntity = new GenreJpaEntity(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt()
        );
        aGenre.getCategories().stream()
                .forEach(anEntity::addCategory);

        return anEntity;
    }

    public Genre toAggregate() {

        return Genre.with(
                GenreID.from(getId()),
                getName(),
                isActive(),
                getCategoriesIDs(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<GenreCategoryJpaEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<GenreCategoryJpaEntity> categories) {
        this.categories = categories;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<CategoryID> getCategoriesIDs(){

        return getCategories().stream()
                .map(it -> CategoryID.from(it.getId().getCategoryId())).toList();
    }

    private void addCategory(final CategoryID anId){
        this.categories.add(GenreCategoryJpaEntity.from(this, anId));
    }
    private void removeCategory(final CategoryID anId){
        this.categories.remove(GenreCategoryJpaEntity.from(this, anId));
    }
}
