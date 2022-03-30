package ltd.newbee.mall.exception;

import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.controller.base.BaseController;
import ltd.newbee.mall.util.R;
import ltd.newbee.mall.util.http.HttpUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * 处理自定义异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    public Object handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        if (HttpUtil.isAjax(request)) {
            return R.error(e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", e.getMessage());
        return new ModelAndView("error/500", map);
    }
}
