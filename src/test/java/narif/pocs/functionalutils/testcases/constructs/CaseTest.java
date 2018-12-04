/**
 * 
 */
package narif.pocs.functionalutils.testcases.constructs;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import narif.pocs.functionalutils.constructs.Case;
import narif.pocs.functionalutils.constructs.Result;
import narif.pocs.functionalutils.functions.Consumer;
import narif.pocs.functionalutils.functions.Effect;
import narif.pocs.functionalutils.functions.Function;

/**
 * @author Najeeb
 *
 */
@DisplayName("Case Spec:")
public class CaseTest {

	private static final Logger LOG = LoggerFactory.getLogger(CaseTest.class);

	@Test
	@DisplayName("Case class abstracts the if...else ladder")
	public void testCase() {
		
		final String EMAIL_PATTERN_STRING = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
		final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);

		Effect<String> successEffect = s -> LOG.info("Email Sent to: " + s);
		Effect<String> failureEffect = s -> LOG.error("Error Message Logged for invalid email: " + s);
		
		/* Use SOPs instead of Logger to have some fun :-)
		 * Effect<String> successEffect = s -> System.out.println("Email Sent to: " + s);
		 * Effect<String> failureEffect = s -> System.err.println("Error Message Logged for invalid email: " + s); 
		*/


		Function<String, Result<String>> emailChecker = s -> Case.match(
				Case.matchCase(() -> Result.success(s)),
				Case.matchCase(() -> s == null, () -> Result.failure("Email address should never be null")),
				Case.matchCase(() -> s.trim().length() == 0, () -> Result.failure("Email can not be left empty")),
				Case.matchCase(() -> !EMAIL_PATTERN.matcher(s).matches(), () -> Result.failure("Invalid email("+s+") provided")));
		
		
		emailChecker.apply("some@gaail.com").bind(successEffect, failureEffect);
		emailChecker.apply(null).bind(successEffect, failureEffect);
		emailChecker.apply("     ").bind(successEffect, failureEffect);
		emailChecker.apply("john.doe@abc.com").bind(successEffect, failureEffect);
		emailChecker.apply("abc").bind(successEffect, failureEffect);
		emailChecker.apply("narif.poc@ggg.com").bind(successEffect, failureEffect);
	}
	
	@Test
	@DisplayName("Simple test case to see if Custom Consumer can be used where javaUtilConsumer is needed")
	public void testConsumerUsage() {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
		Consumer<Integer> custumConsumer = i -> System.out.println(i);
		list.forEach(custumConsumer);
	}

}
