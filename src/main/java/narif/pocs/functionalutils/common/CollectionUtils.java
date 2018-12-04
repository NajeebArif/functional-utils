/**
 * 
 */
package narif.pocs.functionalutils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	 * @param list
	 * @param element
	 * @return append the element at the end of the list and return unmodifiable list
	 */
	public static <T> List<T> append(List<T> list, T element){
		List<T> ts = copy(list);
		ts.add(element);
		return Collections.unmodifiableList(ts);
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

}
