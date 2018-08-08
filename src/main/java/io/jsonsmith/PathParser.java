package io.jsonsmith;

import io.jsonsmith.ast.PropertyPath;

public interface PathParser {
    PropertyPath parse(CharSequence source);
}
