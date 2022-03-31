package ltd.newbee.mall.controller.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ltd.newbee.mall.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected <T> Page<T> getPage(HttpServletRequest request) {
        String pageNumber1 = request.getParameter(Constants.PAGE_NUMBER);
        long pageNumber = Long.parseLong(StringUtils.isNotEmpty(pageNumber1) ? pageNumber1 : "1");
        String pageSize1 = request.getParameter(Constants.PAGE_SIZE);
        long pageSize = Long.parseLong(StringUtils.isNotEmpty(pageSize1) ? pageSize1 : "10");
        return getPage(request, pageNumber, pageSize);
    }

    private <T> Page<T> getPage(HttpServletRequest request, long pageNumber, long pageSize) {
        String sortName = request.getParameter("sidx");
        String sortOrder = request.getParameter("order");
        Page<T> tPage = new Page<>(pageNumber, pageSize);
        if (StringUtils.isNotEmpty(sortName)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(sortName.replaceAll("[A-Z]", "_$0").toLowerCase());
            if (Constants.ORDER_DESC.equals(sortOrder)) {
                orderItem.setAsc(false);
            }
            tPage.addOrder(orderItem);
        }
        return tPage;
    }

}
