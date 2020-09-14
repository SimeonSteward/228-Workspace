package cs228hw1.stats.parsers;

import cs228hw1.stats.IParser;

/**
 * A parser which parses to integers
 * @author Simeon Steward
 */
public class intParser implements IParser<Integer> {

    @Override
    public Integer parse(String str) {
        return Integer.parseInt(str);
    }
}
