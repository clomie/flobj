package net.clomie.jsonsmith.parser;

import static org.jparsec.Parsers.*;
import static org.jparsec.Scanners.*;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;

import net.clomie.jsonsmith.PathParser;
import net.clomie.jsonsmith.ast.ArrayIndex;
import net.clomie.jsonsmith.ast.ObjectKey;
import net.clomie.jsonsmith.ast.Property;
import net.clomie.jsonsmith.ast.PropertyPath;
import net.clomie.jsonsmith.exception.PathParsingException;

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
    private static final Parser<ArrayIndex> ARRAY_NOTATION = INDEX.between(isChar('['), isChar(']')).map(ArrayIndex::new);

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
