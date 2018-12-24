package net.clomie.jsonsmith.parser;

import static org.jparsec.Parsers.*;
import static org.jparsec.Scanners.*;

import org.jparsec.Parser;
import org.jparsec.Parser.Reference;
import org.jparsec.error.ParserException;

import net.clomie.jsonsmith.PathParser;
import net.clomie.jsonsmith.ast.ArrayIndex;
import net.clomie.jsonsmith.ast.ObjectKey;
import net.clomie.jsonsmith.ast.Property;
import net.clomie.jsonsmith.ast.PropertyPath;
import net.clomie.jsonsmith.exception.PathParsingException;

/**
 * Grammer
 *
 * <pre>
 * ArrayIndex ::= [ INTEGER ]
 * ObjectKey ::= . IDENTIFIER | [' STRING ']
 * Property  ::= ListIndex | ObjectKey
 * Path      ::= Property Path | Property
 * </pre>
 */
public class JqPathParser implements PathParser {

    private static final Parser<Integer> INDEX = INTEGER.map(Integer::valueOf);
    private static final Parser<ArrayIndex> ARRAY_NOTATION = inBrackets(INDEX).map(ArrayIndex::new);

    private static final Parser<String> DOT_IDENTIFIER = sequence(isChar('.'), IDENTIFIER);
    private static final Parser<String> STRING_IN_BRACKET = inBrackets(StringLiterals.SINGLE_QUOTED_STRING);
    private static final Parser<ObjectKey> OBJECT_NOTATION = or(DOT_IDENTIFIER, STRING_IN_BRACKET).map(ObjectKey::new);

    private static final Parser<Property> PROPERTY_NOTATION = or(ARRAY_NOTATION, OBJECT_NOTATION);

    private static <T> Parser<T> inBrackets(Parser<T> parser) {
        return parser.between(isChar('['), isChar(']'));
    }

    private static Parser<PropertyPath> propertyPath() {
        Reference<PropertyPath> ref = Parser.newReference();
        Parser<PropertyPath> multiple = sequence(PROPERTY_NOTATION, ref.lazy(), PropertyPath::new);
        Parser<PropertyPath> single = PROPERTY_NOTATION.map(PropertyPath::new);
        Parser<PropertyPath> parser = multiple.or(single);
        ref.set(parser);
        return parser;
    }

    private Parser<PropertyPath> parser;

    public JqPathParser() {
        this.parser = propertyPath();
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
