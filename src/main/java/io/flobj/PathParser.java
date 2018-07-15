package io.flobj;

import io.flobj.ast.PropertyPath;

public interface PathParser {
    PropertyPath parse(CharSequence source);
}
