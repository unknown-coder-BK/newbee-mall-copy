package ltd.newbee.mall;

import ltd.newbee.mall.core.service.CouponUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class calculateEndDateTest {
    @Resource
    CouponUserService couponUserService;

    @Test
    public void test1(){
        couponUserService.calculateEndDate((short) 1);
    }

}
