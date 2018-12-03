/**
 * 
 */
package narif.pocs.functionalutils.constructs.testcases;

import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import narif.pocs.functionalutils.constructs.Case;
import narif.pocs.functionalutils.constructs.Result;
import narif.pocs.functionalutils.functions.Effect;
import narif.pocs.functionalutils.functions.Function;
import narif.pocs.functionalutils.functions.Supplier;

/**
 * @author Najeeb
 *
 */
@DisplayName("Case Spec:")
public class CaseTest {

	private static final Logger LOG = LoggerFactory.getLogger(CaseTest.class);

	private static final String EMAIL_PATTERN_STRING = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);

//	private static Effect<String> successEffect = s -> LOG.info("Email Sent to: " + s);
//	private static Effect<String> failureEffect = s -> LOG.error("Error Message Logged for invalid email: " + s);
	
	private static Effect<String> successEffect = s -> System.out.println("Email Sent to: " + s);
	private static Effect<String> failureEffect = s -> System.err.println("Error Message Logged for invalid email: " + s);


	private static Function<String, Result<String>> emailChecker = s -> Case.match(
			Case.matchCase(() -> Result.success(s)),
			Case.matchCase(() -> s == null, () -> Result.failure("Email address should never be null")),
			Case.matchCase(() -> s.trim().length() == 0, () -> Result.failure("Email can not be left empty")),
			Case.matchCase(() -> !EMAIL_PATTERN.matcher(s).matches(), () -> Result.failure("Invalid email("+s+") provided")));

	@Test
	@DisplayName("Case class abstracts the if...else ladder")
	public void testCase() {
		
		final String EMAIL_PATTERN_STRING = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
		final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);

//		Effect<String> successEffect = s -> LOG.info("Email Sent to: " + s);
//		Effect<String> failureEffect = s -> LOG.error("Error Message Logged for invalid email: " + s);
		
		Effect<String> successEffect = s -> System.out.println("Email Sent to: " + s);
		Effect<String> failureEffect = s -> System.err.println("Error Message Logged for invalid email: " + s);


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

}
