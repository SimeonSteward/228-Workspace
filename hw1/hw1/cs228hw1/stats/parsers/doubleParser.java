package cs228hw1.stats.parsers;

import cs228hw1.stats.IParser;

/**
 * A Parser which parsers to doubles
 * @author Simeon Steward
 */
public class doubleParser implements IParser<Double> {

    @Override
    public Double parse(String str) {
        return Double.parseDouble(str);
    }
}
