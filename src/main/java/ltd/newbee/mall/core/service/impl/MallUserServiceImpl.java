package ltd.newbee.mall.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.core.dao.MallUserDao;
import ltd.newbee.mall.core.entity.Coupon;
import ltd.newbee.mall.core.entity.CouponUser;
import ltd.newbee.mall.core.entity.MallUser;
import ltd.newbee.mall.core.service.CouponService;
import ltd.newbee.mall.core.service.CouponUserService;
import ltd.newbee.mall.core.service.MallUserService;
import ltd.newbee.mall.util.security.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MallUserServiceImpl extends ServiceImpl<MallUserDao, MallUser> implements MallUserService {
    @Resource
    private CouponService couponService;

    @Resource
    private CouponUserService couponUserService;


    @Override
    public boolean register(String loginName, String password) {
        MallUser mallUser = new MallUser();
        mallUser.setLoginName(loginName);
        mallUser.setNickName(UUID.randomUUID().toString().substring(0, 5));
        mallUser.setPasswordMd5(Md5Utils.hash(password));
        if (!save(mallUser)) {
            return false;
        }
        List<Coupon> coupons = couponService.list(Wrappers.<Coupon>lambdaQuery()
                .eq(Coupon::getCouponType, 1));
        coupons.stream().map(coupon ->{
            CouponUser couponUser = new CouponUser();
            couponUser.setUserId(mallUser.getUserId());
            couponUser.setCouponId(coupon.getCouponId());
            Date endDate = couponUserService.calculateEndDate(coupon.getDays());
            return null;
        });
        return false;
    }
}
