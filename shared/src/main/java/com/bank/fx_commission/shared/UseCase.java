package com.bank.fx_commission.shared;

public interface UseCase<T,R> {
    R execute(T input);
}
