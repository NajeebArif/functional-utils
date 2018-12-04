/**
 * 
 */
package narif.pocs.functionalutils.testcases.common;

import static narif.pocs.functionalutils.common.CollectionUtils.append;
import static narif.pocs.functionalutils.common.CollectionUtils.list;
import static narif.pocs.functionalutils.common.CollectionUtils.tail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import narif.pocs.functionalutils.common.CollectionUtils;
import narif.pocs.functionalutils.functions.Function;

import static narif.pocs.functionalutils.common.CollectionUtils.*;

/**
 * @author Najeeb
 *
 */
@DisplayName("Collection Utils Specs:")
public class CollectionUtilsTest {
	
	List<Integer> li;
	
	@BeforeEach
	public void init() {
		li = CollectionUtils.list(1,2,3,4,5);
	}
	
	@Test
	@DisplayName("Map method should apply a function to give a list")
	public void testGenericMapMethod() {
		List<Integer> integerList = Arrays.asList(1,2,3,4,5,6);
		Function<Integer, Double> fromIntToDouble = i -> i * 2.0d;
		List<Double> doubleList = CollectionUtils.map(integerList, fromIntToDouble);
		assertThat(doubleList).isNotNull().isNotEmpty();
	}
	
	@Test
	@DisplayName("All the list() methods return an immutable list")
	public void testVariousListMethods() {
		List<Integer> li = CollectionUtils.list();
		assertThat(li).isEmpty();
		assertThrows(Exception.class, ()->li.add(1), ()->"The returned list should be unmodifiable");
		
		List<Integer> li2 = CollectionUtils.list(Arrays.asList(1,2));
		assertThat(li2).isNotNull().isNotEmpty().hasSize(2);
		assertThrows(Exception.class, ()->li2.add(1), ()->"The returned list should be unmodifiable");
		
		List<Integer> li3 = CollectionUtils.list(1);
		assertThat(li3).isNotNull().isNotEmpty().hasSize(1);
		assertThrows(Exception.class, ()->li3.add(1), ()->"The returned list should be unmodifiable");
		
		List<Integer> li4 = CollectionUtils.list(1,2,3);
		assertThat(li4).isNotNull().isNotEmpty().hasSize(3);
		assertThrows(Exception.class, ()->li4.add(1), ()->"The returned list should be inmodifiable");
		
	}
	
	@Test
	@DisplayName("head method returns the first element of the list")
	public void testHeadMethod() {
		int head = CollectionUtils.head(li);
		assertThat(head).isEqualTo(1);
	}
	
	@Test
	@DisplayName("tail method returns the rest of the list except its head(first element)")
	public void testForTail() {
		List<Integer> tailList = tail(li);
		assertThat(tailList).isNotNull().isNotEmpty().doesNotContain(1).containsExactly(2,3,4,5);
	}
	
	@Test
	@DisplayName("append method appends an element at the end of the list")
	public void testAppend() {
		List<Integer> appendedList = append(li, 6);
		assertThat(appendedList).isNotNull().isNotEmpty().hasSize(6).endsWith(6);
	}
	
	@Test
	public void testFold() {
		Integer result = foldLeft(li, 0, x->y-> x+y);
		assertThat(result).isGreaterThan(0).isEqualTo(15);
	}
	
	@Test
	@DisplayName("left fold should result")
	public void testLeftFold() {
		final String leftFoldResult = "(((((0 + 1) + 2) + 3) + 4) + 5)";
		String identity = "0";
		Function<String, Function<Integer, String>> fn = x-> y-> "("+x+" + "+y+")";
		String result = foldLeft(li, identity, fn);
		assertThat(result).isNotNull().isNotEmpty().isEqualTo(leftFoldResult);
	}
	
	@Test
	@DisplayName("foldRight should result (1 + (2 + (3 + (4 + (5 + 0)))))")
	public void testFoldRight() {
		final String foldRightResult = "(1 + (2 + (3 + (4 + (5 + 0)))))";
		String identity = "0";
		Function<Integer, Function<String, String>> fn = x-> y-> "("+x+" + "+y+")";
		String result = foldRight(li, identity, fn);
		assertThat(result).isNotNull().isNotEmpty().isEqualTo(foldRightResult);
	}
	
	@Test
	@DisplayName("prepend to a list can be accomplished by using foldLeft")
	public void testPrepend() {
		List<Integer> result = prepend(0, li);
		assertThat(result).isNotNull().isNotEmpty().hasSize(6).startsWith(0);
	}

}
