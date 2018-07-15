package io.flobj.ast;

public interface Property {

	Object commit(Object context, Object value);

	Object get(Object context);
}
