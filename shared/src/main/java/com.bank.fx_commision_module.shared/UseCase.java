package com.bank.fx_commission_module.shared;

public interface UseCase<T,R> {
    R execute(T input);
}
