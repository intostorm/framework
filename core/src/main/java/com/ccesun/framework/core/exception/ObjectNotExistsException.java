package com.ccesun.framework.core.exception;

/**
 * 此异常表示对象不存在，通常在访问未持久化的对象时触发
 *
 * @author Jaron
 */
public class ObjectNotExistsException extends RuntimeException {

    private static final long serialVersionUID = 1016710351718841119L;

    public ObjectNotExistsException() {
        super();
    }

    public ObjectNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotExistsException(String message) {
        super(message);
    }

    public ObjectNotExistsException(Throwable cause) {
        super(cause);
    }

}
