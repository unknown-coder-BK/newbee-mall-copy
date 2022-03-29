package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.constant.Constants;
import ltd.newbee.mall.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;


@Controller
public class MallUserController {
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
        R r = new R();
        r.setCode(1);
        return r;
    }
}
