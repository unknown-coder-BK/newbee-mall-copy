package ltd.newbee.mall.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ltd.newbee.mall.controller.base.BaseController;
import ltd.newbee.mall.core.entity.Seckill;
import ltd.newbee.mall.core.entity.vo.SeckillVO;
import ltd.newbee.mall.core.service.SeckillService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/seckill")
public class SeckillManagerController extends BaseController {

    private static final String PREFIX = "admin/seckill";

    @Resource
    SeckillService seckillService;


    @GetMapping
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "seckill");
        return PREFIX + "/seckill";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public IPage<Seckill> list(SeckillVO seckillVO, HttpServletRequest request) {
        Page<Seckill> page = getPage(request);
        return seckillService.selectPage(page, seckillVO);
    }
}
