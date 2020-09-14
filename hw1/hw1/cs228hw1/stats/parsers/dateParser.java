package cs228hw1.stats.parsers;

import cs228hw1.stats.IParser;

/**
 * A parser which returns the time value in format YEARMODA or YR_MO_DA_HR_MN
 * @author Simeon Steward
 */
public class dateParser implements IParser<Integer> {

    @Override
    public Integer parse(String str) {
        return Integer.parseInt(str.substring(0,8));
    }
}
