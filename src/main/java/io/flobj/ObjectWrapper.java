package io.flobj;

import io.flobj.ast.PropertyPath;
import io.flobj.parser.JqPathParser;

public class ObjectWrapper {

    private Object obj;
    private PathParser parser;

    public ObjectWrapper(Object obj) {
        this(obj, new JqPathParser());
    }

    public ObjectWrapper(Object obj, PathParser parser) {
        this.obj = obj;
        this.parser = parser;
    }

    public void commit(String path, Object value) {
        commit(parser.parse(path), value);
    }

    void commit(PropertyPath path, Object value) {
        obj = path.commit(obj, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T pick(String path) {
        PropertyPath accessor = parser.parse(path);
        return (T) accessor.pick(obj);
    }

    public Object unwrap() {
        return obj;
    }

}
