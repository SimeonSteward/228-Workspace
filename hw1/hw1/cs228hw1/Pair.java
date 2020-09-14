package cs228hw1;

/**
 * @author Simeon Steward
 * @param <K> The type of the first value in this pair
 * @param <V> The type of the second value in this pair
 */
public class Pair<K,V> {
    private K key;
    private V value;

    /**
     *Returns the 1st value of this pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the 2nd value of this pair
     */
    public V getValue() {
        return value;
    }

    /**
     * Constructor for this pair
     * @param key 1st value in this pair
     * @param value 2nd value in this pair
     */
    public Pair(K key, V value){
        this.key=key;
        this.value=value;
    }
}
