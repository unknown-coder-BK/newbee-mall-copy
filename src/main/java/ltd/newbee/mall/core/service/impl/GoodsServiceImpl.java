package ltd.newbee.mall.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.core.dao.GoodsDao;
import ltd.newbee.mall.core.entity.Goods;
import ltd.newbee.mall.core.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements GoodsService {

}
