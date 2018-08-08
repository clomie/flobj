package io.jsonsmith.ast;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import io.jsonsmith.exception.UnexpectedContextException;

public class ObjectKey implements Property {

    private final String key;

    public ObjectKey(String key) {
        this.key = Objects.requireNonNull(key);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object commit(Object context, Object value) {

        if (context == null) {
            Map map = new LinkedHashMap();
            map.put(key, value);
            return map;
        }

        if (context instanceof Map) {
            Map map = new LinkedHashMap((Map) context);
            map.put(key, value);
            return map;
        }

        throw new UnexpectedContextException(Map.class, context);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object get(Object context) {

        if (context == null) {
            return null;
        }

        if (context instanceof Map) {
            Map map = (Map) context;
            return map.get(key);
        }

        throw new UnexpectedContextException(Map.class, context);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ObjectKey)) {
            return false;
        }
        ObjectKey other = (ObjectKey) obj;
        return this.key == other.key;
    }

    @Override
    public String toString() {
        return "." + key;
    }
}