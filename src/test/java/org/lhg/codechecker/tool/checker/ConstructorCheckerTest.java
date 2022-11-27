package org.lhg.codechecker.tool.checker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.lhg.codechecker.tool.Store;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConstructorCheckerTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenZero() {
        assertThatThrownBy(() -> {
            Checker checker = new ConstructorChecker(0);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNegative() {
        assertThatThrownBy(() -> {
            Checker checker = new ConstructorChecker(-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnTrueWhenNotFoundIllegalConstructors() {
        // given
        Checker checker = new ConstructorChecker(2);
        List<Store<Object>> stores = makeStores();
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(true);
    }

    @Test
    void shouldReturnFalseWhenFoundIllegalConstructors() {
        // given
        Checker checker = new ConstructorChecker(1);
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
        stores.add(Store.of(clazz.getPackageName(), clazz.getDeclaredConstructors()));
        return stores;
    }

    static class SampleClass {
        public SampleClass() {

        }

        public SampleClass(int x) {

        }
    }
}