package narif.pocs.functionalutils.constructs;

import narif.pocs.functionalutils.functions.Supplier;

/**
 * @author Najeeb
 * 
 * Case class will be used to abstract if...else/switch...case construct
 * Instead of having a if...else ladder match function can be provided
 * with a default case(an outcome abstract in Result class) followed 
 * by varargs of case instances, where each case instance will hold 
 * a tuple of Condition to execute and the result for that condition.
 * 
 * IMP: this way we abstract the if...else/switch...case constructs 
 * from our business logic methods and put them at a single place i.e.
 * in the Case class. This results in no more complex if else ladder
 * or very hard to read ternary operators.
 *
 * @param <T>
 */
public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {

	public Case(Supplier<Boolean> conditionSupplier, Supplier<Result<T>> resultSupplier) {
		super(conditionSupplier, resultSupplier);
	}
	
	public static <T> Case<T> matchCase(Supplier<Boolean> condition, Supplier<Result<T>> value){
		return new Case<>(condition,value);
	}
	
	public static <T> DefaultCase<T> matchCase(Supplier<Result<T>> value){
		return new DefaultCase<>(()->true,value);
	}
	
	@SafeVarargs
	public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... cases){
		for(Case<T> aCase: cases) {
			if(aCase.getFirstValue().get()) return aCase.getSecondValue().get();
		}
		return defaultCase.getSecondValue().get();
	}
	
	private static class DefaultCase<T> extends Case<T>{
		private DefaultCase(Supplier<Boolean> conditionSupplier, Supplier<Result<T>> resultSupplier) {
			super(conditionSupplier, resultSupplier);
		}
	}
}
