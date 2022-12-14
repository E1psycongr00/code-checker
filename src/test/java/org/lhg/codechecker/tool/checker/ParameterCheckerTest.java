package org.lhg.codechecker.tool.checker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.lhg.codechecker.tool.Store;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParameterCheckerTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenZero() {
        assertThatThrownBy(() -> {
            Checker checker = new ParameterChecker(0);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNegative() {
        assertThatThrownBy(() -> {
            Checker checker = new ParameterChecker(-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnTrueWhenNotFoundIllegalParameters() {
        // given
        Checker checker = new ParameterChecker(2);
        List<Store<Object>> stores = makeStores();
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(true);
    }

    @Test
    void shouldReturnFalseWhenFoundIllegalMethods() {
        // given
        Checker checker = new ParameterChecker(1);
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
        stores.add(Store.of(clazz.getPackageName(), clazz.getDeclaredMethods()));
        return stores;
    }

    static class SampleClass {
        public void hello(int x, int y) {

        }

    }
}