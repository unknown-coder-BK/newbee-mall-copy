package ltd.newbee.mall.util.http;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    /**
     * <p>
     * 判断请求是否为 AJAX
     * </p>
     *
     * @param request 当前请求
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}