/**
 * 
 */
package narif.pocs.functionalutils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import narif.pocs.functionalutils.functions.Effect;
import narif.pocs.functionalutils.functions.Function;

/**
 * @author Najeeb
 *
 */
public class CollectionUtils {
	
	/**
	 * @return an empty unmodifiable list
	 */
	public static <T> List<T> list(){
		return Collections.emptyList();
	}
	
	/**
	 * @param t
	 * @return a singleton list with the given element
	 */
	public static <T> List<T> list(T t){
		return Collections.singletonList(t);
	}
	
	/**
	 * @param list list with which list has to be created
	 * @return unmodifiable list after making a defensive copy of the original list
	 */
	public static <T> List<T> list(List<T> list){
		return Collections.unmodifiableList(new ArrayList<>(list));
	}
	
	@SafeVarargs
	public static <T> List<T> list(T... t){
		return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
	}
	
	/**
	 * @param list
	 * @return the first element of the list
	 */
	public static <T> T head(List<T> list) {
		if(list == null || list.isEmpty()) throw new IllegalArgumentException("Input List can not be null/empty");
		return list.get(0);
	}
	
	/**
	 * @param list
	 * @return the tail of the list(excluding the head of first element) which is unmodifiable
	 */
	public static <T> List<T> tail(List<T> list){
		if(list == null || list.isEmpty()) throw new IllegalArgumentException("Input List can not be null/empty");
		List<T> workList = copy(list);
		workList.remove(0);
		return Collections.unmodifiableList(workList);
	}
	
	/**
	 * append method makes a defensive copy of its first argument(through a call to
	 * private copy method), adds the second argument to it, and then returns the
	 * modified list wrapped in an "IMMUTABLE VIEW"
	 * @param list
	 * @param element
	 * @return append the element at the end of the list and return unmodifiable list
	 */
	public static <T> List<T> append(List<T> list, T element){
		List<T> ts = copy(list);
		ts.add(element);
		return Collections.unmodifiableList(ts);
	}
	
	/**
	 * @param list to be left folded 
	 * @param identity to be used for left fold
	 * @param fn function to perform fold
	 * @return left folded value
	 */
	public static <T, U> U foldLeft(List<T> list, U identity, Function<U, Function<T,U>> fn) {
		return foldLeft_(list, identity, fn).eval();
	}
	
	/**
	 * Stack Safe foldLeft helper method using TCE
	 * @param ts
	 * @param identity
	 * @param f
	 * @return
	 */
	private static <T,U> TailCall<U> foldLeft_(List<T> ts, U identity, Function<U, Function<T,U>> f){
		return ts.isEmpty()
				? TailCall.ret(identity): TailCall.sus(()->foldLeft_(tail(ts), f.apply(identity).apply(head(ts)), f));
	}
	
	/**
	 * As fold right should start from the end of the list, therefore list needs to be iterated
	 * in the reverse order.
	 * @param list
	 * @param identity
	 * @param fn
	 * @return
	 */
	public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> fn) {
		return foldRight_(identity, reverse(list), fn).eval();
	}
	
	private static <T,U> TailCall<U> foldRight_(U acc, List<T> ls, Function<T, Function<U,U>> f){
		return ls.isEmpty()?
				TailCall.ret(acc):
					TailCall.sus(()-> foldRight_(f.apply(head(ls)).apply(acc), tail(ls), f));
	}
	
	public static <T> List<T> reverse(List<T> ts){
		List<T> result = new ArrayList<>();
		for(int i = ts.size()-1; i>=0; i--)
			result.add(ts.get(i));
		return Collections.unmodifiableList(result);
	}
	
	public static <T> List<T> reverseFoldLeftAndPrepend(List<T> ts){
		return foldLeft(ts, list(), l->i-> prepend(i,l));
	}
	
	public static <T> List<T> reverseFoldLeftAndAppend(List<T> ts){
		return foldLeft(ts, list(), l -> i -> (
					foldLeft(l, list(i), x-> y-> append(x,y))
				));
	}

	public static <T> List<T> prepend(T t, List<T> list){
		return foldLeft(list, list(t), x->y->append(x,y));
	}
	
	private static <T> List<T> copy(List<T> list){
		return new ArrayList<>(list);
	}
	
	public static <T,U> List<U> map(List<T> list, Function<T,U> f){
		List<U> result = new ArrayList<>();
		for(T t: list)
			result.add(f.apply(t));
		return result;
	}
	
	public static <T, U> List<U> mapViaFoldLeft(List<T> ts, Function<T, U> fn){
		return foldLeft(ts, list(), x-> y-> append(x, fn.apply(y)));
	}
	
	public static <T, U> List<U> mapViaFoldRight(List<T> ts, Function<T, U> fn){
		return foldRight(ts, list(), x-> y-> prepend(fn.apply(x), y));
	}
	
	public static <T> void forEach(Collection<T> cl, Effect<T> e) {
		for(T t: cl) e.apply(t);
	}
	
	public static <T> List<T> unfold(T seed, Function<T, T> fn, Function<T, Boolean> p){
		List<T> result = new ArrayList<>();
		T temp = seed;
		while(p.apply(temp)) {
			result = append(result, temp);
			temp = fn.apply(temp);
		}
		return result;
	}
	
	/**
	 * Recursive stack safe range
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Integer> range(int start, int end){
		return range_(list(),start,end).eval();
	}
	
	private static TailCall<List<Integer>> range_(List<Integer> acc, Integer start, Integer end){
		return end<=start
				? TailCall.ret(acc) : TailCall.sus(()-> range_(append(acc, start), start+1, end));
	}

}
