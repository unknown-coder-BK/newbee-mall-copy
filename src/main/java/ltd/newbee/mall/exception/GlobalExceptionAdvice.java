package ltd.newbee.mall.exception;

import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.util.R;
import ltd.newbee.mall.util.http.HttpUtil;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    /**校验类异常*/
    @ExceptionHandler({BindException.class})
    public Object handleBindException(BindException e, HttpServletRequest request) {
        System.out.println(e);
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return R.error(defaultMessage);
    }

    /**
     * 处理404异常， eg：springboot中404异常会自动找到templates/error目录下的404页面文件
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handle404Exception(NoHandlerFoundException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        if (HttpUtil.isAjax(request)) {
            return R.error("您请求路径不存在，请检查url！");
        }
        return new ModelAndView("error/404");
    }


}
