package io.jsonsmith.ast;

public interface Property {

	Object commit(Object context, Object value);

	Object get(Object context);
}
