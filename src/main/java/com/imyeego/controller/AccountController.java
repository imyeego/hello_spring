package com.imyeego.controller;

import com.imyeego.exception.BalanceNotEnoughException;
import com.imyeego.pojo.BaseResult;
import com.imyeego.pojo.TransferEntity;
import com.imyeego.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/transfer/{name}", method = RequestMethod.POST)
    public BaseResult transfer(@PathVariable String name, @RequestBody TransferEntity transferEntity){
        try {
            accountService.transfer(transferEntity.getFromId(), transferEntity.getToId(), transferEntity.getMoney());
        } catch (BalanceNotEnoughException e) {
            return new BaseResult(200, e.getMessage());
        }
        return new BaseResult(200, "转账成功!");

    }


}
