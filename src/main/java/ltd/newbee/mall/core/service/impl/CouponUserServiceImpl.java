package ltd.newbee.mall.core.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.core.dao.CouponUserDao;
import ltd.newbee.mall.core.entity.CouponUser;
import ltd.newbee.mall.core.service.CouponUserService;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

@Service
public class CouponUserServiceImpl extends ServiceImpl<CouponUserDao, CouponUser> implements CouponUserService {
    @Override
    public Date calculateEndDate(Short days) {
        LocalDate startLocalDate = LocalDate.now();
        LocalDate endLocalDate = startLocalDate.plusDays(days);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = endLocalDate.atStartOfDay();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zone);
        Instant instant = zonedDateTime.toInstant();
        Date from1 = Date.from(instant);
        Date from = Date.from(endLocalDate.atStartOfDay().atZone(zone).toInstant());
        return from;
    }
}
