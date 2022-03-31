package ltd.newbee.mall.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ltd.newbee.mall.core.entity.Seckill;
import ltd.newbee.mall.core.entity.vo.SeckillVO;

import java.util.Map;

public interface SeckillDao extends BaseMapper<Seckill> {

    IPage<Seckill> selectListPage(Page page, SeckillVO seckill);
}
