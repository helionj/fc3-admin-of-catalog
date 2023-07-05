package com.helion.admin.catalog.domain.video;

import com.helion.admin.catalog.domain.validation.Error;
import com.helion.admin.catalog.domain.validation.ValidationHandler;
import com.helion.admin.catalog.domain.validation.Validator;

public class VideoValidator extends Validator{

    private final int TITLE_MAX_LENGTH = 255;
    private final int MIN_LENGTH = 3;
    private final int DESCRIPTION_MAX_LENGTH = 4_000;

    private final Video video;

    protected VideoValidator(final Video aVideo, final ValidationHandler aHandler) {
        super(aHandler);
        this.video = aVideo;
    }

    @Override
    public void validate() {
        checkTitleConstraints();
        checkDescriptionConstraints();
        checkLaunchedAtConstraints();
        checkRatingConstraints();
    }

    private void checkTitleConstraints() {
        final var title = this.video.getTitle();
        if(title == null){
            this.validationHandler().append(new Error("'title' should not be null"));
            return;
        }
        if(title.isBlank()){
            this.validationHandler().append(new Error("'title' should not be empty"));
            return;
        }

        final var length = title.trim().length();
        if(length < MIN_LENGTH || length > TITLE_MAX_LENGTH){
            this.validationHandler().append(new Error("'title' must be between 3 and 255 characters"));
        }
    }

    private void checkDescriptionConstraints() {
        final var description = this.video.getDescription();
        if(description == null){
            this.validationHandler().append(new Error("'description' should not be null"));
            return;
        }
        if(description.isBlank()){
            this.validationHandler().append(new Error("'description' should not be empty"));
            return;
        }

        final var length = description.trim().length();
        if(length < MIN_LENGTH || length > DESCRIPTION_MAX_LENGTH){
            this.validationHandler().append(new Error("'description' must be between 3 and 4000 characters"));
        }
    }
    private void checkLaunchedAtConstraints() {
        final var launchedAt = this.video.getLaunchedAt();
        if(launchedAt == null){
            this.validationHandler().append(new Error("'launchedAt' should not be null"));
        }

    }
    private void checkRatingConstraints() {
        final var rating = this.video.getRating();
        if(rating == null){
            this.validationHandler().append(new Error("'rating' should not be null"));
        }

    }
}