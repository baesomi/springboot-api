package com.prj.sm.web.exception;

public class MemberNotFoundException extends RuntimeException {
    private long id;

    public MemberNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
