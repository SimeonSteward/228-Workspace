package cs228hw1.stats.parsers;

import cs228hw1.stats.IParser;

/**
 * A parser which returns the day value from a string in  format YEARMODA or
 * @author Simeon Steward
 */
public class dayParser implements IParser<Integer> {

    @Override
    public Integer parse(String str) {
        int day = Integer.parseInt(str.substring(6,8));
        return day;
    }
}
