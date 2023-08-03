package com.practice.redis.annotation;

import java.lang.annotation.*;

/**
 * 锁的注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Lock {

    String name() default "";

    String key() default "";

    /**
     * 持锁时间
     * @return
     */
    long leaseTime() default 5000L;

    /**
     * 等待时间
     * @return
     */
    long waitTime() default 60000L;

    /**
     * 是否异步
     * @return
     */
    boolean async() default false;

    /**
     * 是否是公平锁
     * @return
     */
    boolean fair() default false;
}
