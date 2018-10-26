package com.notes.core;

public class BaseType {
    @SuppressWarnings("unchecked")
    <E> Class<E> getMapping() {
        return (Class<E>)this.getClass().getAnnotation(Mapping.class).type();
    }
}
