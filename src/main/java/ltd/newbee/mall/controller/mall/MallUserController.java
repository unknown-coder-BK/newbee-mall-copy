package ltd.newbee.mall.controller.mall;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ltd.newbee.mall.constant.Constants;
import ltd.newbee.mall.core.entity.MallUser;
import ltd.newbee.mall.core.service.MallUserService;
import ltd.newbee.mall.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class MallUserController {
    @Autowired
    private MallUserService mallUserService;
//    @GetMapping("login")
//    public String logout(HttpSession httpSession) {
//        httpSession.removeAttribute(MALL_USER_SESSION_KEY);
//        return "mall/login";
//    }


    @GetMapping("register")
    public String registerPage() {
        return "mall/register";
    }

    @ResponseBody
    @PostMapping("register")
    public R register(@RequestParam("loginName") String loginName,
                      @RequestParam("verifyCode") String verifyCode,
                      @RequestParam("password") String password,
                      HttpSession session) {
        String kaptchaCode = (String) session.getAttribute(Constants.MALL_VERIFY_CODE_KEY);
        if (!StringUtils.equalsIgnoreCase(verifyCode, kaptchaCode)) {
            return R.error("验证码错误");
        }
        List<MallUser> list = mallUserService.list(Wrappers.<MallUser>lambdaQuery()
                .eq(MallUser::getLoginName, loginName).last("limit 1"));
        if (CollectionUtils.isNotEmpty(list)) {
            return R.error("该账户名已存在");
        }
        return R.result(mallUserService.register(loginName, password));
    }
}
