package com.notes.core;

public class BaseType {
    @SuppressWarnings("unchecked")
    <T> Class<T> getMapping() {
        return (Class<T>)this.getClass().getAnnotation(Mapping.class).type();
    }
}
