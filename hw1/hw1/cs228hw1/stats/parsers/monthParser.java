package cs228hw1.stats.parsers;

import cs228hw1.stats.IParser;

/**
 * A parser when Given a String in format YR_MO_DA_HR_MN, returns the Month it represents.
 * @author Simeon Steward
 */
public class monthParser implements IParser<Integer> {
    @Override
    public Integer parse(String str) {
        int month = Integer.parseInt(str.substring(4,6));
        return month;
    }
}
