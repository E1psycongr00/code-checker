package org.lhg.codechecker.tool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.lhg.codechecker.tool.checker.ConstructorChecker;
import org.junit.jupiter.api.Test;

class CodeCheckerTest {


    @Test
    void returnFalseWhenDisobeyRules() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .limitConstructors(3)
                .limitFields(6)
                .limitMethods(10)
                .limitParameters(2)
                .build();

        // when
        boolean checkAll = codeChecker.checkAll("org", true);
        System.out.println(codeChecker.getMessage());

        // then
        assertThat(checkAll).isEqualTo(false);
    }

    @Test
    void returnMessageWhenDisobeyRules() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .limitConstructors(3)
                .limitFields(6)
                .limitMethods(10)
                .limitParameters(2)
                .build();

        // when
        codeChecker.checkAll("org", true);
        System.out.println(codeChecker.getMessage());

        // then
        assertThat(codeChecker.getMessage()).isNotEmpty();
    }

    @Test
    void returnTrueWhenObeyTheRules() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .limitConstructors(3)
                .limitFields(6)
                .limitMethods(20)
                .limitParameters(3)
                .build();

        // when
        boolean checkAll = codeChecker.checkAll("org", true);
        System.out.println(codeChecker.getMessage());

        // then
        assertThat(checkAll).isEqualTo(true);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInputInvalidPackagePath() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .limitConstructors(3)
                .limitFields(6)
                .limitMethods(10)
                .limitParameters(3)
                .build();

        // expect
        assertThatThrownBy(() -> {
            boolean checkAll = codeChecker.checkAll("org.message", true);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void returnFalseWhenUseGetter() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingGetterMethod("org.lhg", true);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(false);
    }

    @Test
    void returnTrueWhenNotUseGetterClasses() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingGetterMethod(Scanners.class, ConstructorChecker.class);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(true);
    }

    @Test
    void returnFalseWhenUseGetterOneClass() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingGetterMethod(Store.class);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(false);
    }

    @Test
    void returnTrueWhenNotUseSetterOneClass() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingSetterMethod(Store.class);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(true);
    }

    @Test
    void returnTrueWhenNotUseSetter() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingSetterMethod("org.lhg.codechecker.tool.message", true);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(true);
    }

    @Test
    void returnFalseWhenUseSetter() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingSetterMethod("org.lhg", true);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(false);
    }

    @Test
    void returnTrueWhenNotUserSetter() {
        // given
        CodeChecker codeChecker = CodeChecker.rules()
                .build();

        // when
        boolean check = codeChecker.checkNotUsingSetterMethod(Store.class, ConstructorChecker.class);

        // then
        System.out.println(codeChecker.getMessage());
        assertThat(check).isEqualTo(true);
    }
}