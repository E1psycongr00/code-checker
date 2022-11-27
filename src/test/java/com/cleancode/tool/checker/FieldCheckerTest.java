package com.cleancode.tool.checker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cleancode.tool.Store;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FieldCheckerTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenZero() {
        assertThatThrownBy(() -> {
            Checker checker = new FieldChecker(0);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNegative() {
        assertThatThrownBy(() -> {
            Checker checker = new FieldChecker(-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnTrueWhenNotFoundIllegalFields() {
        // given
        Checker checker = new FieldChecker(2);
        List<Store<Object>> stores = makeStores();
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(true);
    }

    @Test
    void shouldReturnFalseWhenFoundIllegalFields() {
        // given
        Checker checker = new FieldChecker(1);
        List<Store<Object>> stores = makeStores();
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(false);
    }

    private List<Store<Object>> makeStores() {
        List<Store<Object>> stores = new ArrayList<>();
        Class<?> clazz = SampleClass.class;
        stores.add(Store.of(clazz.getPackageName(), clazz.getDeclaredFields()));
        return stores;
    }

    static class SampleClass {
        private int x;
        private int y;
    }
}