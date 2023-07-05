package com.helion.admin.catalog.infrastructure.castmember.models;

import com.helion.admin.catalog.domain.Fixture;
import com.helion.admin.catalog.infrastructure.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JacksonTest
public class UpdateCastMemberRequestTest {

    @Autowired
    private JacksonTester<UpdateCastMemberRequest> json;

    @Test
    public void testUnMarshall() throws IOException {

        final var expectedName= Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var json = """
                {
                    "name": "%s",
                    "type": "%s"
                }
                """.formatted(
                expectedName,
                expectedType

        );

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("type", expectedType);
    }
}