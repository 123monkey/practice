package com.practice.future.meituan;

/**
 * @author tech
 */
@FunctionalInterface
public interface ThriftAsyncCall {
    void invoke() throws TException ;
}