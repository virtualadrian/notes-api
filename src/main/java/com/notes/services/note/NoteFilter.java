package com.notes.services.note;

public enum NoteFilter {
    ARCHIVED(),
    FAVORITES(),
    PINNED(),
    TRASH(),
    ALL();

    @Override
    public String toString() {
        return this.name();
    }
}
