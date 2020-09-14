package cs228hw1.stats.parsers;

import cs228hw1.stats.IParser;

/**
 * A parser when Given a String in format YR_MO_DA_HR_MN, returns the Year it represents.
 * @author Simeon Steward
 */
public class yearParser implements IParser<Integer> {

    @Override
    public Integer parse(String str) {
        return Integer.parseInt(str.substring(0,4));
    }
}
