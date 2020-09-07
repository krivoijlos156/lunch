package ru.golunch;


import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMatcher<T> {
    private final String[] fieldsToIgnore;

    TestMatcher(String... fieldsToIgnore) {
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, fieldsToIgnore);
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
    }
}
