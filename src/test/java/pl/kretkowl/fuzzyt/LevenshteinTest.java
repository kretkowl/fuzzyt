package pl.kretkowl.fuzzyt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinTest {

    public Levenshtein levenshtein = new Levenshtein();

    @ParameterizedTest
    @MethodSource("provideStringForRatio")
    public void bookSimilarityRatioShouldBeEqualTo(String other, float ratio) {
        assertEquals(ratio, Levenshtein.ldRatio("book", other), "for " + other);
    }

    @ParameterizedTest
    @MethodSource("provideStringForTokenRatio")
    public void helloWorldTokenRatioShouldBeEqualTo(String other, float ratio) {
        assertEquals(ratio, Levenshtein.tokenSortRatio("hello world", other), "for " + other);
    }

    @Test
    public void shouldTokenizeAndSortExpression() {
        assertEquals("abcdef", Levenshtein.tokenSortString("def abc"));
    }

    public static Stream<Arguments> provideStringForRatio() {
        return Stream.of(
                Arguments.of("book", 1f),
                Arguments.of("books", (9 - 1)/9f),
                Arguments.of("look", (8 - 1)/8f),
                Arguments.of("deer", (8 - 4)/8f),
                Arguments.of("", 0.0f));
    }

    public static Stream<Arguments> provideStringForTokenRatio() {
        return Stream.of(
                Arguments.of("hello world", 1f),
                Arguments.of("world hello", 1f),
                Arguments.of("world gello", (20 - 1)/20f),
                Arguments.of("", 0.f));
    }
}
