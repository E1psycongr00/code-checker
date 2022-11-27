package com.cleancode.tool.message.bool;

import com.cleancode.tool.Store;

/**
 * Getter / Setter Message Generator
 */
public interface BooleanMessageGenerator<V> {

    String generateMessage(Store<V> store, boolean isAllow);
}
