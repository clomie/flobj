package net.clomie.jsonsmith.ast;

import java.util.List;
import java.util.stream.Collectors;

public class PropertyPath {

    private final List<Property> props;

    public PropertyPath(List<Property> props) {
        this.props = props;
    }

    public Object commit(Object context, Object value) {
        if (props.isEmpty()) {
            return value;
        }
        Property head = props.get(0);
        PropertyPath path = new PropertyPath(props.subList(1, props.size()));
        return head.commit(context, path.commit(head.get(context), value));
    }

    public Object pick(Object context) {
        if (props.isEmpty()) {
            return context;
        }
        Property head = props.get(0);
        PropertyPath path = new PropertyPath(props.subList(1, props.size()));
        return path.pick(head.get(context));
    }

    @Override
    public int hashCode() {
        return props.hashCode();
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
        return props.equals(other.props);
    }

    @Override
    public String toString() {
        return props
            .stream()
            .map(Object::toString)
            .collect(Collectors.joining());
    }

}
