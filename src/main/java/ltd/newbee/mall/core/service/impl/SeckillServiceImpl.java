package ltd.newbee.mall.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.constant.Constants;
import ltd.newbee.mall.core.dao.SeckillDao;
import ltd.newbee.mall.core.entity.Seckill;
import ltd.newbee.mall.core.entity.vo.ExposerVO;
import ltd.newbee.mall.core.entity.vo.SeckillVO;
import ltd.newbee.mall.core.service.SeckillService;
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
}
