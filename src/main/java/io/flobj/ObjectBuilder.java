package io.flobj;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flobj.ast.PropertyPath;
import io.flobj.parser.JqPathParser;

public class ObjectBuilder {

    private Map<PropertyPath, Object> entryMap;
    private PathParser parser;

    public ObjectBuilder() {
        this(new JqPathParser());
    }

    public ObjectBuilder(PathParser parser) {
        this.parser = parser;
        this.entryMap = new HashMap<>();
    }

    public ObjectBuilder put(String path, Object value) {
        PropertyPath accessor = parser.parse(path);
        entryMap.put(accessor, value);
        return this;
    }

    public Object build() {
        return build(null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object build(Object initial) {
        ObjectWrapper wrapper = new ObjectWrapper(initial);
        entryMap.forEach(wrapper::commit);
        Object result = wrapper.unwrap();

        if (result instanceof List) {
            return Collections.unmodifiableList((List) result);
        }
        if (result instanceof Map) {
            return Collections.unmodifiableMap((Map) result);
        }
        return result;
    }

}
