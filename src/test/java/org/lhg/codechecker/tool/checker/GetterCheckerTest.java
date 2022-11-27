package org.lhg.codechecker.tool.checker;

import static org.assertj.core.api.Assertions.assertThat;

import org.lhg.codechecker.tool.Store;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetterCheckerTest {

    @Test
    void shouldReturnFalseWhenUsingPublicGetter() {
        // given
        Checker checker = new GetterChecker();
        List<Store<Object>> stores = makeStores(PublicGetter.class);
        StringBuilder buffer = new StringBuilder();

        // when
        boolean check = checker.check(stores, buffer);

        // then
        assertThat(check).isEqualTo(false);
    }

    @Test
    void shouldReturnTrueWhenUsingPrivateGetter() {
        // given
        Checker checker = new GetterChecker();
        List<Store<Object>> stores = makeStores(PrivateGetter.class);
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

    static class PublicGetter {
        public int getHello() {
            return 1;
        }
    }

    static class PrivateGetter {
        private int getHello() {
            return 1;
        }
    }
}