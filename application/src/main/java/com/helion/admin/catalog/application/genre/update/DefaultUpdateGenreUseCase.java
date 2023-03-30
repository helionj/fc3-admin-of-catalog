package com.helion.admin.catalog.application.genre.update;

import com.helion.admin.catalog.domain.category.Category;
import com.helion.admin.catalog.domain.category.CategoryGateway;
import com.helion.admin.catalog.domain.category.CategoryID;
import com.helion.admin.catalog.domain.exceptions.DomainException;
import com.helion.admin.catalog.domain.exceptions.NotFoundException;
import com.helion.admin.catalog.domain.exceptions.NotificationException;
import com.helion.admin.catalog.domain.genre.Genre;
import com.helion.admin.catalog.domain.genre.GenreGateway;
import com.helion.admin.catalog.domain.genre.GenreID;
import com.helion.admin.catalog.domain.validation.Error;
import com.helion.admin.catalog.domain.validation.ValidationHandler;
import com.helion.admin.catalog.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultUpdateGenreUseCase extends UpdateGenreUseCase {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultUpdateGenreUseCase(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway) {
        Objects.nonNull(this.categoryGateway = categoryGateway);
        Objects.nonNull(this.genreGateway = genreGateway);
    }

    @Override
    public UpdateGenreOutput execute(final UpdateGenreCommand aCommand) {
        final var anId = GenreID.from(aCommand.id());
        final var aName = aCommand.name();
        final var isActive = aCommand.isActive();
        final var categories = toCategoryID(aCommand.categories());

        final var aGenre = this.genreGateway.findById(anId).orElseThrow(notFound(anId));
        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.validate(() -> aGenre.update(aName, isActive, categories));

        if(notification.hasErrors()){
            throw new NotificationException("Could not update Aggregate Genre %s".formatted(aCommand.id()), notification);
        }
        return UpdateGenreOutput.from(this.genreGateway.update(aGenre));
    }

    private List<CategoryID> toCategoryID(List<String> categories) {
        return categories.stream()
                .map(CategoryID::from)
                .toList();
    }

    private Supplier<DomainException> notFound(final GenreID anId) {
        return () -> NotFoundException.with(Genre.class, anId);
    }

    private ValidationHandler validateCategories(final List<CategoryID> ids) {
        final var notification = Notification.create();
        if(ids == null || ids.isEmpty()){
            return notification;
        }
        final var retrievedIds = categoryGateway.existsByIds(ids);

        if(ids.size() != retrievedIds.size()){
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(CategoryID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }
}
