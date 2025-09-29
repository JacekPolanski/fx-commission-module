package com.bank.fxcommission.shared;

public interface UseCase<T,R> {
    R execute(T input);
}
