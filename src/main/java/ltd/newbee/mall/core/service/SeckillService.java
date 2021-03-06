package ltd.newbee.mall.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.core.entity.Seckill;
import ltd.newbee.mall.core.entity.vo.ExposerVO;
import ltd.newbee.mall.core.entity.vo.MallUserVO;
import ltd.newbee.mall.core.entity.vo.SeckillSuccessVO;
import ltd.newbee.mall.core.entity.vo.SeckillVO;

public interface SeckillService extends IService<Seckill> {

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param seckillVO 秒杀对象
     * @return 分页数据
     */
    IPage<Seckill> selectPage(Page<Seckill> page, SeckillVO seckillVO);

    /**
     * 秒杀地址暴露接口
     *
     * @param seckillId 秒杀商品ID
     * @return 秒杀服务接口地址暴露类
     */
    ExposerVO exposerUrl(Long seckillId);

    /**
     * 执行秒杀最终逻辑
     *
     * @param seckillId 秒杀商品ID
     * @param userVO    用户VO
     * @return 用户秒杀成功VO
     */
    SeckillSuccessVO executeSeckillFinal(Long seckillId, MallUserVO userVO);
}
