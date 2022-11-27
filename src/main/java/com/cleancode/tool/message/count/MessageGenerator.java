package com.cleancode.tool.message.count;

import com.cleancode.tool.Store;

/**
 * The message generator generates a message of out-of-rules data
 */
@FunctionalInterface
public interface MessageGenerator<V> {

    String generateMessage(Store<V> store, int limitCount);
}

