package org.lhg.codechecker.tool.message.count;

import org.lhg.codechecker.tool.Store;

/**
 * The message generator generates a message of out-of-rules data
 */
@FunctionalInterface
public interface MessageGenerator<V> {

    String generateMessage(Store<V> store, int limitCount);
}

