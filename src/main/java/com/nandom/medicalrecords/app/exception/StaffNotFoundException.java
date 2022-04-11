package com.nandom.medicalrecords.app.exception;

public class StaffNotFoundException  extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public StaffNotFoundException(String msg) {
        super(msg);
    }
}
