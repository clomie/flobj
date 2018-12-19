package net.clomie.jsonsmith;

import net.clomie.jsonsmith.parser.JqPathParser;

public final class Jsonsmith {

    public static Object commit(Object target, String path, Object value) {
        return new JqPathParser().parse(path).commit(target, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T pick(Object target, String path) {
        return (T) new JqPathParser().parse(path).pick(target);
    }

    private Jsonsmith() {
        //
    }
}
