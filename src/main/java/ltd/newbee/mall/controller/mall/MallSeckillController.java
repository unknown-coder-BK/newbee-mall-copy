package ltd.newbee.mall.controller.mall;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ltd.newbee.mall.annotation.RepeatSubmit;
import ltd.newbee.mall.constant.Constants;
import ltd.newbee.mall.controller.base.BaseController;
import ltd.newbee.mall.core.entity.Goods;
import ltd.newbee.mall.core.entity.Seckill;
import ltd.newbee.mall.core.entity.vo.ExposerVO;
import ltd.newbee.mall.core.entity.vo.MallUserVO;
import ltd.newbee.mall.core.entity.vo.SeckillSuccessVO;
import ltd.newbee.mall.core.service.GoodsService;
import ltd.newbee.mall.core.service.SeckillService;
import ltd.newbee.mall.exception.BusinessException;
import ltd.newbee.mall.redis.RedisCache;
import ltd.newbee.mall.util.R;
import ltd.newbee.mall.util.security.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequestMapping("seckill")
public class MallSeckillController extends BaseController {
    @Resource
    private SeckillService seckillService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private GoodsService goodsService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @GetMapping("list")
    @ResponseBody
    public String list(HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) {
        // 判断缓存中是否有当前秒杀商品列表页面,以免每次都去获取列表
        String html = redisCache.getCacheObject(Constants.SECKILL_GOODS_LIST_HTML);
        if (StringUtils.isNotBlank(html)) {
            return html;
        }
        List<Seckill> seckillList = seckillService.list(
                new QueryWrapper<Seckill>().eq("status", 1).orderByDesc("seckill_rank"));
        List<Map<String, Object>> list = seckillList.stream().map(seckill -> {
            Map<String, Object> map = new HashMap<>();
            map.put("seckillId", seckill.getSeckillId());
            map.put("goodsId", seckill.getGoodsId());
            map.put("seckillPrice", seckill.getSeckillPrice());
            Goods goods = goodsService.getById(seckill.getGoodsId());
            map.put("goodsName", goods.getGoodsName());
            map.put("originalPrice", goods.getOriginalPrice());
            map.put("goodsCoverImg", goods.getGoodsCoverImg());
            map.put("goodsIntro", goods.getGoodsIntro());
            return map;
        }).collect(Collectors.toList());
        request.setAttribute("seckillList", list);
        // 缓存秒杀商品列表页
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("mall/seckill-list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisCache.setCacheObject(Constants.SECKILL_GOODS_LIST_HTML, html, 100, TimeUnit.HOURS);
        }
        return html;
    }

    @GetMapping("detail/{seckillId}")
    @ResponseBody
    public String detail(@PathVariable("seckillId") Long seckillId,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         Model model) {
        // 判断缓存中是否有当前秒杀商品详情页面
        String html = redisCache.getCacheObject(Constants.SECKILL_GOODS_DETAIL_HTML + seckillId);
//        if (StringUtils.isNotBlank(html)) {
//            return html;
//        }
        Seckill seckill = seckillService.getById(seckillId);
        Long goodsId = seckill.getGoodsId();
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("seckillPrice", seckill.getSeckillPrice());
        map.put("startDate", seckill.getSeckillBegin().getTime());
        map.put("endDate", seckill.getSeckillEnd().getTime());
        Goods goods = goodsService.getById(seckill.getGoodsId());
        map.put("goodsName", goods.getGoodsName());
        map.put("originalPrice", goods.getOriginalPrice());
        map.put("goodsCoverImg", goods.getGoodsCoverImg());
        map.put("goodsIntro", goods.getGoodsIntro());
        map.put("goodsDetailContent", goods.getGoodsDetailContent());
        long now = System.currentTimeMillis();
        //秒杀商品开始时间
        long startAt = seckill.getSeckillBegin().getTime();
        //秒杀商品结束时间
        long endAt = seckill.getSeckillEnd().getTime();
        int miaoshaStatus;
        int remainSeconds;
        if (now < startAt) {// 秒杀还没开始，倒计时(秒)
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {// 秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {// 秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        request.setAttribute("goodsDetail", map);
        request.setAttribute("seckillId", seckill.getSeckillId());
        request.setAttribute("seckillStatus", miaoshaStatus);
        request.setAttribute("remainSeconds", remainSeconds);
        // 缓存秒杀商品详情页
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("mall/seckill-detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisCache.setCacheObject(Constants.SECKILL_GOODS_DETAIL_HTML + seckillId, html, 30, TimeUnit.MINUTES);
        }
        return html;
    }

    @ResponseBody
    @GetMapping("time/now")
    public R getTimeNow() {
        return R.success().add("now", new Date().getTime());
    }

    @ResponseBody
    @PostMapping("{seckillId}/exposer")
    public R exposerUrl(@PathVariable Long seckillId) {
        ExposerVO exposerVO = seckillService.exposerUrl(seckillId);
        return R.success().add("exposer", exposerVO);
    }

    @ResponseBody
    @RepeatSubmit // 接口防重复提交注解
    // 接口限流注解
    @PostMapping(value = "/{seckillId}/{md5}/execution")
    public R execute(@PathVariable Long seckillId,
                     @PathVariable String md5, HttpSession session) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(5);
        if (md5 == null || !md5.equals(Md5Utils.hash(seckillId))) {
            throw new BusinessException("秒杀商品不存在");
        }
        MallUserVO userVO = (MallUserVO) session.getAttribute(Constants.MALL_USER_SESSION_KEY);
        SeckillSuccessVO seckillSuccessVO = seckillService.executeSeckillFinal(seckillId, userVO);
        return R.success().add("seckillSuccess", seckillSuccessVO);
    }
}
