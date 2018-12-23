package net.clomie.jsonsmith.ast;

import java.util.Objects;

public class PropertyPath {

    private final Property head;
    private final PropertyPath tail;

    public PropertyPath(Property head) {
        this(head, null);
    }

    public PropertyPath(Property head, PropertyPath tail) {
        this.head = head;
        this.tail = tail;
    }

    public Object commit(Object context, Object value) {
        if (tail == null) {
            return head.commit(context, value);
        } else {
            return head.commit(context, tail.commit(head.get(context), value));
        }
    }

    public Object pick(Object context) {
        if (tail == null) {
            return head.get(context);
        } else {
            return tail.pick(head.get(context));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyPath)) {
            return false;
        }
        PropertyPath other = (PropertyPath) obj;
        return Objects.equals(head, other.head) && Objects.equals(tail, other.tail);
    }

    @Override
    public String toString() {
        if (tail != null) {
            return head.toString();
        } else {
            return head + "," + tail;
        }
    }

}
