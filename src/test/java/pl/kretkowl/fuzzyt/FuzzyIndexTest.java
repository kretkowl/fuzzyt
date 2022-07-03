package pl.kretkowl.fuzzyt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FuzzyIndexTest {

    private static FuzzyIndex fi = new FuzzyIndex(.5f);

    @BeforeAll
    public static void initializeF() {
        fi.add("ABC");
        fi.add("ABD");
        fi.add("DEF");
        fi.add("ABC EWG");
    }

    @ParameterizedTest
    @MethodSource("prepareFuzzyIndexCalls")
    public void shouldRetrieveList(String input, Float treshold, List<String> result) {
        assertEquals(result, fi.filter(input, treshold), "for " + input);    
    }

    public static Stream<Arguments> prepareFuzzyIndexCalls() {
        return Stream.of(
                Arguments.of("ABC", .99f, List.of("ABC")),
                Arguments.of("ABC", .8f, List.of("ABC", "ABD")),
                Arguments.of("DEF", .9f, List.of("DEF")),
                Arguments.of("EWG", .66f, List.of("ABC EWG"))
                );
    }
}
