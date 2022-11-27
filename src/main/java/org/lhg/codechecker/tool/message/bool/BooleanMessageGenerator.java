package org.lhg.codechecker.tool.message.bool;

import org.lhg.codechecker.tool.Store;

/**
 * Getter / Setter Message Generator
 */
public interface BooleanMessageGenerator<V> {

    String generateMessage(Store<V> store, boolean isAllow);
}
