package com.formssi.mall.account.controller;

import com.formssi.mall.account.service.AccountService;
import com.formssi.mall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


/**
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 14:00:13
 */
@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/deduct")
    public R deduct(@RequestParam("userId") Integer userId, @RequestParam("amount") Integer amount,
                    @RequestParam("errorPhase") Integer errorPhase){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("amount",amount);
        map.put("errorPhase",errorPhase);
        accountService.deductAccount(map);

        return R.ok();
    }

    @GetMapping("/tccDeduct")
    public R tccDeduct(@RequestParam("userId") Integer userId, @RequestParam("amount") Integer amount,
                       @RequestParam("errorPhase") Integer errorPhase){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("amount",amount);
        map.put("errorPhase",errorPhase);
        accountService.tccDeductAccount(map);

        return R.ok();
    }



}
