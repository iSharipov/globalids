package io.github.isharipov.globalids.us.zipcode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * A ZIP Code - https://en.wikipedia.org/wiki/ZIP_Code
 * Regular expression - https://stackoverflow.com/questions/2577236/regex-for-zip-code/#answer-2577239
 */
public class ZipCode implements Model {

    public static final String REGEX = "^\\d{5}(?:[-\\s]\\d{4})?$";

    /**
     * Returns the Ssn representation of the {@code value} argument.
     *
     * @param value an {@code String}.
     * @return ZIP Code
     */
    @JsonCreator
    public static ZipCode valueOf(final String value) {
        return new ZipCode(value);
    }

    /**
     * First digit representing a certain group of U.S. states
     */
    private final String groupOfStates;

    /**
     * The second and third digits together representing a region in that group
     */
    private final String region;

    /**
     * The fourth and fifth digits representing a group of delivery addresses within that region.
     */
    private final String groupOfAddresses;

    /**
     * Four additional digits to identify a geographic segment within the five-digit delivery area
     */
    private final String geographicSegment;

    public ZipCode(final String zipCode) {
        Pattern pattern = Pattern.compile(REGEX);
        if (!pattern.matcher(zipCode).matches()) {
            throw new ZipCodeValidationException(String.format("%s has invalid format", zipCode));
        }
        String _zipCode = zipCode.replace("-", "").replace(" ", "");
        groupOfStates = _zipCode.substring(0, 1);
        region = _zipCode.substring(1, 3);
        groupOfAddresses = _zipCode.substring(3, 5);
        geographicSegment = (_zipCode.substring(5).length() == 0 ? null : _zipCode.substring(5));
    }

    public static class ZipCodeValidationException extends ValidationException {

        public ZipCodeValidationException(String message) {
            super(message);
        }
    }

    @JsonValue
    public String formattedValue() {
        return groupOfStates + region + groupOfAddresses + (geographicSegment == null ? "" : "-" + geographicSegment);
    }

    public String value() {
        return groupOfStates + region + groupOfAddresses + (geographicSegment == null ? "" : " " + geographicSegment);
    }

    /**
     * A class can be used to convert
     * entity attribute state into database column representation
     * and back again.
     */
    public static class ZipCodeAttributeConverter extends ModelAttributeConverter<ZipCode> {
        @Override
        protected ZipCode instance(String value) {
            return valueOf(value);
        }
    }

    @Target({METHOD, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = ZipCodeConstraintValidator.class)
    public @interface ZipCodeValidator {
        String message() default "{io.github.isharipov.globalids.us.zipcode.ZipCodeValidator.message}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    /**
     * Defines the logic to validate a given constraint for a given object type
     */
    public static class ZipCodeConstraintValidator extends ModelConstraintValidator<ZipCodeValidator, ZipCode> {
        protected void instance(String value) {
            valueOf(value);
        }
    }

    public String getGroupOfStates() {
        return groupOfStates;
    }

    public String getRegion() {
        return region;
    }

    public String getGroupOfAddresses() {
        return groupOfAddresses;
    }

    public String getGeographicSegment() {
        return geographicSegment;
    }
}
