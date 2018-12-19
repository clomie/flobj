package net.clomie.jsonsmith.ast;

import java.util.ArrayList;
import java.util.List;

import net.clomie.jsonsmith.exception.UnexpectedContextException;

public class ArrayIndex implements Property {

    private final int index;

    public ArrayIndex(int index) {
        this.index = index;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object commit(Object context, Object value) {

        if (context == null) {
            List list = new ArrayList();
            while (list.size() <= index) {
                list.add(null);
            }
            list.set(index, value);
            return list;
        }

        if (context instanceof List) {
            List list = new ArrayList((List) context);
            while (list.size() <= index) {
                list.add(null);
            }
            list.set(index, value);
            return list;
        }

        throw new UnexpectedContextException(List.class, context);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object get(Object context) {

        if (context == null) {
            return null;
        }

        if (context instanceof List) {
            List list = (List) context;
            if (list.size() <= index) {
                return null;
            } else {
                return list.get(index);
            }
        }

        throw new UnexpectedContextException(List.class, context);
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArrayIndex)) {
            return false;
        }
        ArrayIndex other = (ArrayIndex) obj;
        return this.index == other.index;
    }

    @Override
    public String toString() {
        return "[" + index + "]";
    }
}
