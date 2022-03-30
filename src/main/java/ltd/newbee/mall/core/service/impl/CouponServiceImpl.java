package ltd.newbee.mall.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.core.dao.CouponDao;
import ltd.newbee.mall.core.entity.Coupon;
import ltd.newbee.mall.core.service.CouponService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponDao, Coupon> implements CouponService {

    @Override
    public Date calculateEndDate(Short days) {
        LocalDate startLocalDate = LocalDate.now();
        LocalDate endLocalDate = startLocalDate.plusDays(days);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(endLocalDate.atStartOfDay().atZone(zone).toInstant());
    }
}

