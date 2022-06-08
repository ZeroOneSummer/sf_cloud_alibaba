package com.formssi.mall.ums.application;

import com.formssi.mall.ums.cmd.LoginCmd;
import com.formssi.mall.ums.dto.LoginDTO;

import java.awt.image.BufferedImage;

/**
 * <p>
 * 后台登录 应用服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
public interface IUmsLoginService {

    BufferedImage getCaptcha(String uuid);

    LoginDTO login(LoginCmd loginCmd);

}
