package com.cleancode.util;

import java.util.List;

public interface Scanner<T> {

    List<T> scan(String packageName);
}
