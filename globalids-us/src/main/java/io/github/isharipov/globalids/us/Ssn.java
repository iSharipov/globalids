package io.github.isharipov.globalids.us;

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
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Social Security number - https://en.wikipedia.org/wiki/Social_Security_number
 * Regular expression - https://www.geeksforgeeks.org/how-to-validate-ssn-social-security-number-using-regular-expression/
 */
public class Ssn implements Model {

    public static final String REGEX = "^(?!666|000|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0{4})\\d{4}$";

    /**
     * Returns the Ssn representation of the {@code value} argument.
     *
     * @param value an {@code String}.
     * @return Ssn
     */
    @JsonCreator
    public static Ssn valueOf(final String value) {
        return new Ssn(value);
    }

    /**
     * The first set of three digits is called the Area Number
     */
    private final String area;
    /**
     * The second set of two digits is called the Group Number
     */
    private final String group;
    /**
     * The final set of four digits is the Serial Number
     */
    private final String serial;

    public Ssn(final String ssn) {
        Pattern pattern = Pattern.compile(REGEX);
        if (!pattern.matcher(ssn).matches()) {
            throw new SsnValidationException(String.format("%s has invalid format", ssn));
        }
        final String _ssn = ssn.replace("-", "");
        area = _ssn.substring(0, 3);
        group = _ssn.substring(3, 5);
        serial = _ssn.substring(5);
    }

    public static class SsnValidationException extends ValidationException {
        public SsnValidationException(String message) {
            super(message);
        }
    }

    @JsonValue
    public String formattedValue() {
        return area + "-" + group + "-" + serial;
    }

    public String value() {
        return area + group + serial;
    }

    /**
     * A class can be used to convert
     * entity attribute state into database column representation
     * and back again.
     */
    public static class SsnAttributeConverter extends ModelAttributeConverter<Ssn> {
        protected Ssn instance(String value) {
            return valueOf(value);
        }
    }

    @Target({METHOD, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = SsnConstraintValidator.class)
    public @interface SsnValidator {
        String message() default "{io.github.isharipov.globalids.us.SsnValidator.message}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }


    /**
     * Defines the logic to validate a given constraint for a given object type
     */
    public static class SsnConstraintValidator extends ModelConstraintValidator<SsnValidator, Ssn> {
        protected void instance(String value) {
            valueOf(value);
        }
    }

    public String getArea() {
        return area;
    }

    public String getGroup() {
        return group;
    }

    public String getSerial() {
        return serial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ssn)) return false;
        Ssn ssn = (Ssn) o;
        return area.equals(ssn.area) &&
                group.equals(ssn.group) &&
                serial.equals(ssn.serial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, group, serial);
    }

    @Override
    public String toString() {
        return "Ssn{" +
                "area='" + area + '\'' +
                ", group='" + group + '\'' +
                ", serial='" + serial + '\'' +
                '}';
    }
}