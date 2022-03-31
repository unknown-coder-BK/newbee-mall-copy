package ltd.newbee.mall.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.core.entity.Seckill;
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
}
