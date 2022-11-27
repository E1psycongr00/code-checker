package com.cleancode.tool.checker;

import static org.assertj.core.api.Assertions.assertThat;

import com.cleancode.tool.Store;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SetterCheckerTest {

    @Test
    void shouldReturnFalseWhenUsingPublicSetter() {
        // given
        Checker checker = new SetterChecker();
        List<Store<Object>> stores = makeStores(PublicSetter.class);
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(false);
    }

    @Test
    void shouldReturnTrueWhenUsingPrivateSetter() {
        // given
        Checker checker = new SetterChecker();
        List<Store<Object>> stores = makeStores(PrivateSetter.class);
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(true);
    }

    List<Store<Object>> makeStores(Class<?> clazz) {
        List<Store<Object>> stores = new ArrayList<>();
        stores.add(Store.of(clazz.getPackageName(), clazz.getDeclaredMethods()));
        return stores;
    }

    static class PublicSetter {
        public int setHello() {
            return 1;
        }
    }

    static class PrivateSetter {
        private int setHello() {
            return 1;
        }
    }
}