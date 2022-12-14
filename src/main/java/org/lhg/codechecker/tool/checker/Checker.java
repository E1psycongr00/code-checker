package org.lhg.codechecker.tool.checker;

import org.lhg.codechecker.tool.Store;
import java.util.List;

/**
 * The Checker finds data that does not follow the rule
 * and then stores a message about that data in a buffer.
 */
@FunctionalInterface
public interface Checker {

    boolean check(List<Store<Object>> scanned, StringBuilder buffer);
}
