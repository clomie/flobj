package io.flobj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.flobj.ast.PropertyPath;
import io.flobj.parser.JqPathParser;

public class ObjectBuilder {

    private List<Entry> entries;
    private PathParser parser;

    public ObjectBuilder() {
        this(new JqPathParser());
    }

    public ObjectBuilder(PathParser parser) {
        this.parser = parser;
        this.entries = new ArrayList<>();
    }

    public ObjectBuilder put(String path, Object value) {
        PropertyPath accessor = parser.parse(path);
        entries.add(new Entry(accessor, value));
        return this;
    }

    public Object build() {
        return build(null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object build(Object initial) {
        Object result = initial;
        for (Entry entry : entries) {
            result = entry.build(result);
        }

        if (result instanceof List) {
            return Collections.unmodifiableList((List) result);
        }
        if (result instanceof Map) {
            return Collections.unmodifiableMap((Map) result);
        }
        return result;
    }

    private static class Entry {
        final PropertyPath path;
        final Object value;

        Entry(PropertyPath path, Object value) {
            this.path = path;
            this.value = value;
        }

        Object build(Object context) {
            return path.commit(context, value);
        }
    }
}
