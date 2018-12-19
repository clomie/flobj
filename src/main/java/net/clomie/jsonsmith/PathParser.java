package net.clomie.jsonsmith;

import net.clomie.jsonsmith.ast.PropertyPath;

public interface PathParser {
    PropertyPath parse(CharSequence source);
}
