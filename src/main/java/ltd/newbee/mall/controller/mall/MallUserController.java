package ltd.newbee.mall.controller.mall;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ltd.newbee.mall.constant.Constants;
import ltd.newbee.mall.core.entity.MallUser;
import ltd.newbee.mall.core.entity.dto.MallUserDTO;
import ltd.newbee.mall.core.entity.vo.MallUserVO;
import ltd.newbee.mall.core.service.MallUserService;
import ltd.newbee.mall.exception.BusinessException;
import ltd.newbee.mall.util.R;
import ltd.newbee.mall.util.http.HttpUtil;
import ltd.newbee.mall.util.security.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class MallUserController {
    @Autowired
    private MallUserService mallUserService;

    @GetMapping("register")
    public String registerPage() {
        return "mall/register";
    }

    @ResponseBody
    @PostMapping("register")
    public R register(@RequestBody  @Validated(value = {MallUserDTO.Register.class}) MallUserDTO mallUserDTO,
                      HttpSession session) {
        String verifyCode = mallUserDTO.getVerifyCode();
        String loginName = mallUserDTO.getLoginName();
        String password = mallUserDTO.getPassword();
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


    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        if (HttpUtil.isAjax(request)) {
            throw new BusinessException("请先登陆！");
        }
        return "mall/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public R doLogin(@RequestBody @Validated(value = {MallUserDTO.Login.class}) MallUserDTO mallUserDTO,
                     @RequestParam("destPath") String destPath,
                     HttpSession session) {
        R success = R.success();
        String loginName = mallUserDTO.getLoginName();
        String password = mallUserDTO.getPassword();
        String verifyCode = mallUserDTO.getVerifyCode();
        String kaptchaCode = (String) session.getAttribute(Constants.MALL_VERIFY_CODE_KEY);
        if (!StringUtils.equalsIgnoreCase(verifyCode, kaptchaCode)) {
            return R.error("验证码错误");
        }
        List<MallUser> list = mallUserService.list(Wrappers.<MallUser>lambdaQuery()
                .eq(MallUser::getLoginName,loginName)
                .eq(MallUser::getPasswordMd5, Md5Utils.hash(password)).last("limit 1")
        );
        if (CollectionUtils.isEmpty(list)) {
            return R.error("账户名称或者密码错误");
        }
        MallUser user = list.get(0);
        if (user.getLockedFlag() == 1) {
            return R.error("该账户已被禁用");
        }
        MallUserVO mallUserVO = new MallUserVO();
        BeanUtils.copyProperties(user, mallUserVO);
        session.setAttribute(Constants.MALL_USER_SESSION_KEY, mallUserVO);
        if (StringUtils.isNotEmpty(destPath) && StringUtils.contains(destPath, "=")) {
            success.add("destPath", destPath.split("=")[1].substring(1));
        }
        return success;
    }
}
