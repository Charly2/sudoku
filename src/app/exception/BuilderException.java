/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.exception;

/**
 *
 * @author Charly
 */
public class BuilderException extends Exception {

    public BuilderException(final String msg) {
        super(msg);
    }

    public BuilderException(final String msg, final Throwable t) {
        super(msg);
        this.setStackTrace(t.getStackTrace());
    }
}
