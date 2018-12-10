package narif.pocs.functionalutils.functions;

import java.util.List;

/**
 * @author Najeeb
 *
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface Function<T, U> extends java.util.function.Function<T, U>{

    U apply(T arg);

    default <V> Function<V, U> compose(final Function<V, T> f){
        return x->apply(f.apply(x));
    }

    default <V> Function<T, V> andThen(final Function<U, V> f){
        return x -> f.apply(apply(x));
    }

    static <T> Function<T, T> identity(){
        return t -> t;
    }

    static <T, U, V> Function<V, U> compose(final Function<T, U> f,
                                            final Function<V, T> g){
        return x -> f.apply(g.apply(x));
    }

    static <T, U, V> Function<T, V> andThen(final Function<T, U> f,
                                            final Function<U, V> g){
        return x -> g.apply(f.apply(x));
    }

    static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> compose(){
        return x -> y -> y.compose(x);
    }

    static <T, U, V> Function<Function<T, U>, Function<Function<V, T>, Function<V, U>>> andThen(){
        return x -> y -> y.andThen(x);
    }

    static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen(){
        return x -> y -> z -> y.apply(x.apply(z));
    }

    static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose(){
        return (Function<U, V> x) -> (Function<T, U> y) -> (T z) -> x.apply(y.apply(z));
    }
    
}
