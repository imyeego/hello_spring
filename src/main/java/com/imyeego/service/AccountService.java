package com.imyeego.service;

import com.imyeego.exception.BalanceNotEnoughException;

public interface AccountService {

    void transfer(final int fromId, final int toId, final double money) throws BalanceNotEnoughException;
}
