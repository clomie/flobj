package io.flobj.parser;

import static org.jparsec.pattern.Patterns.*;

import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Scanners;
import org.jparsec.pattern.CharPredicates;

import io.flobj.exception.PathParsingException;

class StringLiterals {

    private static final Parser<String> CHARACTERS =
        regex("[^\\\\'\\x00-\\x1f\\x7f]")
            .toScanner("char")
            .source();

    private static final Parser<String> ESCAPED_CHARACTER =
        isChar('\\')
            .next(among("'\\/bfnrt"))
            .toScanner("escaped char")
            .source()
            .map(StringLiterals::escape);

    private static final Parser<String> UNICODE_LITERAL =
        string("\\u")
            .next(repeat(4, CharPredicates.IS_HEX_DIGIT))
            .toScanner("unicode literal")
            .source()
            .map(StringLiterals::unicode);

    static final Parser<String> SINGLE_QUOTED_STRING =
        quotedBy(Parsers.or(CHARACTERS, ESCAPED_CHARACTER, UNICODE_LITERAL).many(), Scanners.isChar('\''))
            .map(StringLiterals::join);

    private static final String escape(String input) {
        switch (input.charAt(1)) {
        case '\'':
            return "'";
        case '\\':
            return "\\";
        case '/':
            return "/";
        case 'b':
            return "\b";
        case 'f':
            return "\f";
        case 'n':
            return "\n";
        case 'r':
            return "\r";
        case 't':
            return "\t";
        default:
            throw new PathParsingException("unexpected escaped string");
        }
    }

    private static final String unicode(String input) {
        char c = (char) Integer.parseInt(input.substring(2));
        return String.valueOf(c);
    }

    private static final String join(Iterable<String> list) {
        return String.join("", list);
    }

    private static <T> Parser<T> quotedBy(Parser<T> parser, Parser<Void> quote) {
        return parser.between(quote, quote);
    }

    public static void main(String[] args) {
        String str = SINGLE_QUOTED_STRING.parse("'a\\t\\'\\\\\"'");
        System.out.println(str);
    }
}
