package com.nice.core.netty.message;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface Handler {
    /**
     * @return
     */
    int command();
}
