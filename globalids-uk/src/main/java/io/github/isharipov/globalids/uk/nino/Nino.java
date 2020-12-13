package io.github.isharipov.globalids.uk.nino;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.isharipov.modelframework.Model;
import io.github.isharipov.modelframework.ModelAttributeConverter;
import io.github.isharipov.modelframework.ModelConstraintValidator;
import io.github.isharipov.modelframework.ValidationException;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The National Insurance number - https://en.wikipedia.org/wiki/National_Insurance_number
 * Regular expression - https://www.regexlib.com/REDetails.aspx?regexp_id=527&amp;AspxAutoDetectCookieSupport=1
 */
public class Nino implements Model {
    public static final String REGEX = "^[A-CEGHJ-PR-TW-Z]{1}[A-CEGHJ-NPR-TW-Z]{1}[0-9]{6}[A-DFM]{0,1}$";

    /**
     * Returns the Nino representation of the {@code value} argument.
     *
     * @param value an {@code String}.
     * @return Nino
     */
    @JsonCreator
    public static Nino valueOf(final String value) {
        return new Nino(value);
    }

    /**
     * The number is two prefix letters
     */
    private final String prefix;

    /**
     * Six digits
     */
    private final String digits;

    /**
     * One suffix letter
     */
    private final String suffix;

    public Nino(String nino) {
        Pattern pattern = Pattern.compile(REGEX);
        if (!pattern.matcher(nino).matches()) {
            throw new NinoValidationException(String.format("%s has invalid format", nino));
        }
        prefix = nino.substring(0, 2);
        digits = nino.substring(2, 8);
        suffix = nino.substring(8);
    }

    public static class NinoValidationException extends ValidationException {
        public NinoValidationException(String message) {
            super(message);
        }
    }

    @Override
    public String formattedValue() {
        return prefix + digits + suffix;
    }

    /**
     * A class can be used to convert
     * entity attribute state into database column representation
     * and back again.
     */
    public static class NinoAttributeConverter extends ModelAttributeConverter<Nino> {
        protected Nino instance(String value) {
            return valueOf(value);
        }
    }

    @Target({METHOD, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = NinoConstraintValidator.class)
    public @interface NinoValidator {
        String message() default "{io.github.isharipov.globalids.uk.nino.NinoValidator.message}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    /**
     * Defines the logic to validate a given constraint for a given object type
     */
    public static class NinoConstraintValidator extends ModelConstraintValidator<NinoValidator, Nino> {
        protected void instance(String value) {
            valueOf(value);
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDigits() {
        return digits;
    }

    public String getSuffix() {
        return suffix;
    }
}
