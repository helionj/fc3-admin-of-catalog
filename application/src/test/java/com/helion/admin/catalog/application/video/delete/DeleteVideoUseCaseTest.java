package com.helion.admin.catalog.application.video.delete;

import com.helion.admin.catalog.application.UseCaseTest;
import com.helion.admin.catalog.domain.exceptions.InternalErrorException;
import com.helion.admin.catalog.domain.video.MediaResourceGateway;
import com.helion.admin.catalog.domain.video.VideoGateway;
import com.helion.admin.catalog.domain.video.VideoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Mock
    private MediaResourceGateway mediaResourceGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway, mediaResourceGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteVideo_shouldDeleteIt(){
        final var expectedId = VideoID.unique();

        doNothing().when(videoGateway).deleteById(any());

        doNothing().when(mediaResourceGateway).clearResources(any());

        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        verify(videoGateway).deleteById(eq(expectedId));
        verify(mediaResourceGateway).clearResources(eq(expectedId));
    }


    @Test
    public void givenAnInValidId_whenCallsDeleteVideo_shouldBeOk(){
        final var expectedId = VideoID.from("invalid_id");

        doNothing().when(videoGateway).deleteById(any());

        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        verify(videoGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenCallsDeleteVideoAndGatewayThrowsException_shouldReceiveException(){
        final var expectedId = VideoID.unique();

        doThrow(InternalErrorException.with("Error on delete video", new RuntimeException()))
                .when(videoGateway).deleteById(any());

        Assertions.assertThrows(InternalErrorException.class, () -> this.useCase.execute(expectedId.getValue()));

        verify(videoGateway).deleteById(eq(expectedId));
    }
}
