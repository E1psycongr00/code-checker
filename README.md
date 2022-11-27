
# Code-checker
[![](https://jitpack.io/v/E1psycongr00/code-checker.svg)](https://jitpack.io/#E1psycongr00/code-checker)


우테코 5기 프리코스를 진행하면서 클린 코드에 대해서 고민하던 중 클린 코드도 테스트 자동화하면 어떨까 하는 생각에 제작한 라이브러리입니다.
모두들 우테코 문제가 아니더라도 클린 코드 연습하시면서 도움이 되는 라이브러리가 됬었으면 좋겠습니다. 기본적으로 다음과 같은 기능을 제공합니다.

- util
  - 현재 패키지 포함 하위 패키지 클래스 모두 탐색.
  - 현재 패키지의 클래스만 모두 탐색.

- tool
  - 해당 패키지 범위 내에 클래스에 대하여 다음을 테스트할 수 있음.
    1. 클래스 내 생성자 갯수 제한.
    2. 클래스 내 메서드 갯수 제한.
    3. 클래스 내 필드 갯수 제한.
    4. getter / setter 사용 제한.

실패한 코드들은 체크시 버퍼에 저장되며 getMessage()를 활용해 버퍼에 저장된 패키지 및 실패 정보를 확인할 수 있습니다.

## Install

### gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}


implementation 'com.github.E1psycongr00:code-checker:1.0.0'
```

## Features

### CodeChecker

초기 설정한 rule에 이상이 없다면 true, 규칙에 어긋난 코드가 있는 경우 false를 반환합니다.

갯수 검증의 경우에는 처음에 초기화한 셋팅을 기준으로 초과하는 경우를 체크를 합니다.

#### 초기화하기

```java
CodeChecker codeChecker = CodeChecker.rules()
      .limitConstructors(3)
      .limitFields(6)
      .limitMethods(10)
      .limitParameters(2)
      .build();
```

초기화시 아무런 정보를 입력하지 않으면 기본값은 무한대로 설정됩니다.(실제는 정수 최대값)

#### 모두 검증하기

```java
boolean check = codechecker.checkAll("packageName", isRecursive);
```

갯수제한에 관련 코드 모두 검증합니다. getter, setter의 경우 특수한 도메인에만 적용한다고 생각해서 적용하지 않았습니다.



#### 생성자 갯수 검증하기

```java
boolean check = codechecker.checkConstructorCount("packageName", isRecursive);
```

해당 패키지 클래스의 생성자 갯수를 검증합니다. isRecursive가 true면 하위 패키지까지 모두 체크,
false면 현재 패키지의 클래스에 대해서만 체크합니다.

#### 메서드 갯수 검증하기

```java
boolean check = codechecker.checkMethodCount("packageName", isRecursive);
```

#### 필드 갯수 검증하기

```java
boolean check = codechecker.checkFieldCount("packageName", isRecursive);
```

#### 파라미터 갯수 검증하기

```java
boolean check = codechecker.checkParameterCount("packageName", isRecursive);
```

#### Getter / Setter 검증하기

```java
boolean check = codechecker.checkNotUsingGetterMethod("packageName", isRecursive);
        
boolean check = codechecker.checkNotUsingGetterMethod(A.class, B.class, C.class);
        
boolean check = codechecker.checkNotUsingSetterMethod("packageName", isRecursive);
        
boolean check = codechecker.checkNotUsingSetterMethod(A.class, B.class, C.class);
```
getter / setter 검증의 경우 2가지 형태를 제공합니다. 
- 클래스를 지정해서 테스트 하는 방법
- 패키지를 통해 검증하는 방법


## 사용 예시




### example 1

#### code

```java
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
}
```

### result

```text
parameters: org.lhg.codechecker.tool.Scanners$4 # lambda$scan$0, count: 3.
parameters count must be 2 but 3.

parameters: org.lhg.codechecker.util.FileClassFinderImpl # searchFiles, count: 3.
parameters count must be 2 but 3.

parameters: org.lhg.codechecker.util.FileClassFinderImpl # addClasses, count: 3.
parameters count must be 2 but 3.

parameters: org.lhg.codechecker.util.FileScanner # addClasses, count: 3.
parameters count must be 2 but 3.

methods: org.lhg.codechecker.tool.CodeCheckerTest # [void org.lhg.codechecker.tool.CodeCheckerTest.shouldThrowIllegalArgumentExceptionWhenInputInvalidPackagePath(), void org.lhg.codechecker.tool.CodeCheckerTest.returnTrueWhenNotUseSetter(), void org.lhg.codechecker.tool.CodeCheckerTest.returnFalseWhenUseGetterOneClass(), void org.lhg.codechecker.tool.CodeCheckerTest.returnFalseWhenUseGetter(), void org.lhg.codechecker.tool.CodeCheckerTest.returnTrueWhenNotUserSetter(), void org.lhg.codechecker.tool.CodeCheckerTest.returnFalseWhenUseSetter(), void org.lhg.codechecker.tool.CodeCheckerTest.returnFalseWhenDisobeyRules(), void org.lhg.codechecker.tool.CodeCheckerTest.returnTrueWhenNotUseGetterClasses(), void org.lhg.codechecker.tool.CodeCheckerTest.returnMessageWhenDisobeyRules(), void org.lhg.codechecker.tool.CodeCheckerTest.returnTrueWhenObeyTheRules(), void org.lhg.codechecker.tool.CodeCheckerTest.returnTrueWhenNotUseSetterOneClass(), private static void org.lhg.codechecker.tool.CodeCheckerTest.lambda$shouldThrowIllegalArgumentExceptionWhenInputInvalidPackagePath$0(org.lhg.codechecker.tool.CodeChecker) throws java.lang.Throwable], count: 12.
methods count must be 10 but 12.

methods: org.lhg.codechecker.tool.CodeChecker # [public java.lang.String org.lhg.codechecker.tool.CodeChecker.getMessage(), public boolean org.lhg.codechecker.tool.CodeChecker.checkAll(java.lang.String,boolean), public static org.lhg.codechecker.tool.CodeChecker$ConfigurationBuilder org.lhg.codechecker.tool.CodeChecker.rules(), public boolean org.lhg.codechecker.tool.CodeChecker.checkNotUsingGetterMethod(java.lang.Class[]), public boolean org.lhg.codechecker.tool.CodeChecker.checkNotUsingGetterMethod(java.lang.String,boolean), public boolean org.lhg.codechecker.tool.CodeChecker.checkNotUsingSetterMethod(java.lang.String,boolean), public boolean org.lhg.codechecker.tool.CodeChecker.checkNotUsingSetterMethod(java.lang.Class[]), public boolean org.lhg.codechecker.tool.CodeChecker.checkParameterCount(java.lang.String,boolean), public boolean org.lhg.codechecker.tool.CodeChecker.checkConstructorCount(java.lang.String,boolean), private static org.lhg.codechecker.tool.Store org.lhg.codechecker.tool.CodeChecker.lambda$checkNotUsingGetterMethod$0(java.lang.Class), private static org.lhg.codechecker.tool.Store org.lhg.codechecker.tool.CodeChecker.lambda$checkNotUsingSetterMethod$1(java.lang.Class), public boolean org.lhg.codechecker.tool.CodeChecker.checkMethodCount(java.lang.String,boolean), public boolean org.lhg.codechecker.tool.CodeChecker.checkFieldCount(java.lang.String,boolean)], count: 13.
methods count must be 10 but 13.
```

### example2

#### code

```java
class CodeCheckerTest {

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
}
```

#### result

```text
getter method: org.lhg.codechecker.tool # getValue.
getter method not allowed in package -> org.lhg.codechecker.tool.

getter method: org.lhg.codechecker.tool # getPackageName.
getter method not allowed in package -> org.lhg.codechecker.tool.
```