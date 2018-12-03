package narif.pocs.functionalutils.constructs;

import narif.pocs.functionalutils.functions.Effect;

/**
 * @author Najeeb
 * 
 * Interface to hold result of any computation
 * Abstract bind method binds the successful effect and effect of failure
 *
 * @param <T>
 */
public interface Result<T> {
	
	void bind(Effect<T> success, Effect<String> failure);
	
	public static <T> Result<T> failure(String message){
		return new Failure<>(message);
	}
	
	public static <T> Result<T> success(T value){
		return new Success<>(value);
	}
	
	public class Success<T> implements Result<T>{
		
		private final T value;
		
		private Success(T t) {
			value = t;
		}
		
		@Override
		public void bind(Effect<T> success, Effect<String> failure) {
			success.apply(value);
		}
	}
	
	public class Failure<T> implements Result<T>{
		
		private final String failureMessage;
		
		private Failure(String msg) {
			failureMessage = msg;
		}

		@Override
		public void bind(Effect<T> success, Effect<String> failure) {
			failure.apply(failureMessage);
		}
	}

}
