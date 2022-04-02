package ltd.newbee.mall.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.constant.Constants;
import ltd.newbee.mall.core.dao.SeckillDao;
import ltd.newbee.mall.core.entity.Seckill;
import ltd.newbee.mall.core.entity.vo.ExposerVO;
import ltd.newbee.mall.core.entity.vo.MallUserVO;
import ltd.newbee.mall.core.entity.vo.SeckillSuccessVO;
import ltd.newbee.mall.core.entity.vo.SeckillVO;
import ltd.newbee.mall.core.service.SeckillService;
import ltd.newbee.mall.exception.BusinessException;
import ltd.newbee.mall.redis.RedisCache;
import ltd.newbee.mall.util.security.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillDao, Seckill> implements SeckillService {
    @Resource
    private SeckillDao seckillDao;

    @Autowired
    private RedisCache redisCache;

    @Override
    public IPage<Seckill> selectPage(Page<Seckill> page, SeckillVO seckillVO) {
        return seckillDao.selectListPage(page, seckillVO);
    }

    @Override
    public ExposerVO exposerUrl(Long seckillId) {
        Seckill seckill = redisCache.getCacheObject(Constants.SECKILL_KEY + seckillId);
        if (seckill == null) {
            seckill = getById(seckillId);
            redisCache.setCacheObject(Constants.SECKILL_KEY + seckillId, seckill, 24, TimeUnit.HOURS);
        }
        Date startTime = seckill.getSeckillBegin();
        Date endTime = seckill.getSeckillEnd();
        // 系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new ExposerVO(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 加密
        String md5 = Md5Utils.hash(seckillId);
        return new ExposerVO(true, md5, seckillId);
    }

    /**
     * 秒杀最终方案
     * 1、使用令牌桶算法过滤用户请求<br>
     * 2、使用redis-set数据结构判断用户是否买过秒杀商品<br>
     * 3、返回用户秒杀成功VO
     *
     * @param seckillId 秒杀商品ID
     * @param userVO    秒杀用户VO
     * @return SeckillSuccessVO
     */
    @Override
    public SeckillSuccessVO executeSeckillFinal(Long seckillId, MallUserVO userVO) {
//        // 判断能否在500毫秒内得到令牌，如果不能则立即返回false，不会阻塞程序
//        if (!rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS)) {
//            // System.out.println("短期无法获取令牌，真不幸，排队也瞎排");
//            throw new BusinessException("秒杀失败");
//        }
//        // 判断用户是否购买过秒杀商品
//        if (redisCache.containsCacheSet(Constants.SECKILL_SUCCESS_USER_ID + seckillId, userVO.getUserId())) {
//            throw new BusinessException("您已经购买过秒杀商品，请勿重复购买");
//        }
//        Seckill seckill = redisCache.getCacheObject(Constants.SECKILL_KEY + seckillId);
//        if (seckill == null) {
//            seckill = getById(seckillId);
//            redisCache.setCacheObject(Constants.SECKILL_KEY + seckillId, seckill, 24, TimeUnit.HOURS);
//        }
//        // 判断秒杀商品是否再有效期内
//        long beginTime = seckill.getSeckillBegin().getTime();
//        long endTime = seckill.getSeckillEnd().getTime();
//        Date now = new Date();
//        long nowTime = now.getTime();
//        if (nowTime < beginTime) {
//            throw new BusinessException("秒杀未开启");
//        } else if (nowTime > endTime) {
//            throw new BusinessException("秒杀已结束");
//        }
//
//        SeckillSuccess seckillSuccess = new SeckillSuccess();
//        seckillSuccess.setSeckillId(seckillId);
//        seckillSuccess.setUserId(userVO.getUserId());
//        if (!seckillSuccessService.save(seckillSuccess)) {
         throw new BusinessException("保存用户秒杀商品失败");
//        }
//        SeckillSuccessVO seckillSuccessVO = new SeckillSuccessVO();
//        Long seckillSuccessId = seckillSuccess.getSecId();
//        seckillSuccessVO.setSeckillSuccessId(seckillSuccessId);
//        seckillSuccessVO.setMd5(Md5Utils.hash(seckillSuccessId + Constants.SECKILL_EXECUTE_SALT));
//        return seckillSuccessVO;
    }
}
