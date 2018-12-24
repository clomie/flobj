package net.clomie.jsonsmith.parser;

import static org.jparsec.Parsers.*;
import static org.jparsec.Scanners.*;

import org.jparsec.Parser;
import org.jparsec.Parser.Reference;
import org.jparsec.Scanners;

import net.clomie.jsonsmith.ast.ArrayIndex;
import net.clomie.jsonsmith.ast.ObjectKey;
import net.clomie.jsonsmith.ast.Property;
import net.clomie.jsonsmith.ast.PropertyPath;

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
class PathScanners {

    static final Parser<Integer> INDEX = Scanners.INTEGER.map(Integer::valueOf);
    static final Parser<ArrayIndex> ARRAY_NOTATION = inBrackets(INDEX).map(ArrayIndex::new);

    static final Parser<String> DOT_IDENTIFIER = isChar('.').next(Scanners.IDENTIFIER);
    static final Parser<String> STRING_IN_BRACKET = inBrackets(StringLiterals.SINGLE_QUOTED_STRING);
    static final Parser<ObjectKey> OBJECT_NOTATION = or(DOT_IDENTIFIER, STRING_IN_BRACKET).map(ObjectKey::new);

    static final Parser<Property> PROPERTY_NOTATION = or(ARRAY_NOTATION, OBJECT_NOTATION);

    static <T> Parser<T> inBrackets(Parser<T> parser) {
        return parser.between(isChar('['), isChar(']'));
    }

    static Parser<PropertyPath> propertyPath() {
        Reference<PropertyPath> ref = Parser.newReference();
        Parser<PropertyPath> multiple = sequence(PROPERTY_NOTATION, ref.lazy(), PropertyPath::new);
        Parser<PropertyPath> single = PROPERTY_NOTATION.map(PropertyPath::new);
        Parser<PropertyPath> parser = multiple.or(single);
        ref.set(parser);
        return parser;
    }

}
