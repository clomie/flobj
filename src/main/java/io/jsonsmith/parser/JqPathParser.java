package io.jsonsmith.parser;

import static org.jparsec.Parsers.*;
import static org.jparsec.Scanners.*;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;

import io.jsonsmith.PathParser;
import io.jsonsmith.ast.ListIndex;
import io.jsonsmith.ast.ObjectKey;
import io.jsonsmith.ast.Property;
import io.jsonsmith.ast.PropertyPath;
import io.jsonsmith.exception.PathParsingException;

/**
 * Grammer
 * <pre>
 * ListIndex ::= [ INTEGER ]
 * ObjectKey ::= . INDENTIFIER | [' STRING ']
 * Property  ::= ListIndex | ObjectKey
 * Path      ::= Property | Property Path
 * </pre>
 */
public class JqPathParser implements PathParser {

    private static final Parser<Integer> INDEX = INTEGER.map(Integer::valueOf);
    private static final Parser<ListIndex> ARRAY_NOTATION = INDEX.between(isChar('['), isChar(']')).map(ListIndex::new);

    private static final Parser<ObjectKey> OBJECT_NOTATION =
        or(
            sequence(isChar('.'), IDENTIFIER),
            StringLiterals.SINGLE_QUOTED_STRING.between(isChar('['), isChar(']')))
                .map(ObjectKey::new);

    private static final Parser<Property> PROPERTY_NOTATION = or(ARRAY_NOTATION, OBJECT_NOTATION);

    private static final Parser<PropertyPath> PROPERTY_PATH = PROPERTY_NOTATION.many().map(PropertyPath::new);

    private Parser<PropertyPath> parser;

    public JqPathParser() {
        this.parser = PROPERTY_PATH;
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
