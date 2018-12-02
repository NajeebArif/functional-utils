package narif.poc.functionalutils.datastructures;

public class  Tuple<T, V> {

    private final T firstValue;
    private final V secondValue;

    public Tuple(T firstValue, V secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public T getFirstValue() {
        return firstValue;
    }

    public V getSecondValue() {
        return secondValue;
    }
}
