package com.imyeego.service.impl;

import com.imyeego.exception.BalanceNotEnoughException;
import com.imyeego.mapper.AccountMapper;
import com.imyeego.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String BALANCE_NOT_ENOUGH = "您的账户余额不足!";

    @Autowired
    private AccountMapper accountMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void transfer(int fromId, int toId, double money) throws BalanceNotEnoughException {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(money));

        double fromBalance = accountMapper.get(fromId);
        BigDecimal afterBalance = new BigDecimal(String.valueOf(fromBalance)).subtract(bigDecimal);
        if (afterBalance.doubleValue() >= 0.0){
            accountMapper.set(fromId, afterBalance.doubleValue());
        } else {
            throw new BalanceNotEnoughException(BALANCE_NOT_ENOUGH);
        }
//        int i = 1/0; 异常引起操作回滚
        double toBalance = accountMapper.get(toId);
        afterBalance = new BigDecimal(String.valueOf(toBalance)).add(bigDecimal);
        accountMapper.set(toId, afterBalance.doubleValue());
    }
}
