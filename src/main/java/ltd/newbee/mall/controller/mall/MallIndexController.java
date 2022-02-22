package ltd.newbee.mall.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MallIndexController
 *
 * @author ZhangBen526
 * @date 2022/2/22 15:24
 */
@Controller
public class MallIndexController {

    @GetMapping("index")
    public String index() {
        return "mall/index";
    }
}
