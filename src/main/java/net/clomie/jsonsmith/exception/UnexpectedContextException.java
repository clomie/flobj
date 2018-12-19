package net.clomie.jsonsmith.exception;

public class UnexpectedContextException extends RuntimeException {

    private final Class<?> expectedType;
    private final Object actualValue;

    public UnexpectedContextException(Class<?> expectedType, Object actualValue) {
        super("Context must be " + expectedType + ", but: " + actualValue.getClass());
        this.expectedType = expectedType;
        this.actualValue = actualValue;
    }

    public Class<?> getExpectedType() {
        return expectedType;
    }

    public Object getActualValue() {
        return actualValue;
    }

}
