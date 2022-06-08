package com.formssi.mall.ums.interfaces;

import com.formssi.mall.ums.application.IUmsLoginService;
import com.formssi.mall.ums.application.IUmsUserService;
import com.formssi.mall.ums.cmd.LoginCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.jwt.JwtConstant;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.redis.service.RedisService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;

/**
 * <p>
 * 后台登录 前端控制器
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 23:41:53
 */
@RestController
@RequestMapping("/ums")
public class UmsLoginController {

    @Autowired
    private IUmsLoginService iBmsLoginService;

    @Autowired
    private IUmsUserService iBmsUserService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/getCaptcha")
    public void getCaptcha(@RequestParam("uuid") String uuid, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = iBmsLoginService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @PostMapping("/login")
    public Object login(@RequestBody @Valid LoginCmd loginCmd) {
        return CommonResp.ok(iBmsLoginService.login(loginCmd));
    }

    @PostMapping("/getLoginInfo")
    public Object getLoginInfo() {
        Long userId = jwtTokenService.getUserId();
        return CommonResp.ok(iBmsUserService.getUserInfo(userId));
    }

    @PostMapping("/logout")
    public Object logout() {
        Long userId = jwtTokenService.getUserId();
        redisService.delete(userId + JwtConstant.REDIS_REFRESH_TOKEN_KEY);
        return CommonResp.ok();
    }

}
