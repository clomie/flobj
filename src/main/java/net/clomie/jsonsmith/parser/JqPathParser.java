package net.clomie.jsonsmith.parser;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;

import net.clomie.jsonsmith.PathParser;
import net.clomie.jsonsmith.ast.PropertyPath;
import net.clomie.jsonsmith.exception.PathParsingException;

public class JqPathParser implements PathParser {

    private Parser<PropertyPath> parser;

    public JqPathParser() {
        this.parser = PathScanners.propertyPath();
    }

    @Override
    public PropertyPath parse(CharSequence source) {
        try {
            return parser.parse(source);
        } catch (ParserException e) {
            throw new PathParsingException("Invalid path: " + source, e);
        }
    }
}
