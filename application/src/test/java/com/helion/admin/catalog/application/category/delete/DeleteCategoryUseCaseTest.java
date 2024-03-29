package com.helion.admin.catalog.application.category.delete;

import com.helion.admin.catalog.application.UseCaseTest;
import com.helion.admin.catalog.domain.category.Category;
import com.helion.admin.catalog.domain.category.CategoryGateway;
import com.helion.admin.catalog.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteCategoryUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    public void givenAnValidId_whenCallsDeleteCategory_shouldBeOK(){
        final var aCategory = Category.newCategory("Movies", "Os filmes mais vistos", true);

        final var expectedId = aCategory.getId();
        doNothing().when(categoryGateway).deleteById(eq(expectedId));
        Assertions.assertDoesNotThrow(() ->  useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expectedId);

    }

    @Test
    public void givenAiNValidId_whenCallsDeleteCategory_shouldBeOK(){
        final var expectedId = CategoryID.from("123");
        doNothing().when(categoryGateway).deleteById(eq(expectedId));
        Assertions.assertDoesNotThrow(() ->  useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expectedId);
    }

    public void givenAnValidId_whenGatewayThrowsException_shouldReturnsException(){
        final var aCategory = Category.newCategory("Movies", null, true);

        final var expectedId = aCategory.getId();
        doThrow(new IllegalStateException("Gateway Error")).when(categoryGateway).deleteById(eq(expectedId));
        Assertions.assertThrows(IllegalStateException.class, () ->  useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expectedId);
    }


}
