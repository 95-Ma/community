package motor.community.advice;

import motor.community.Exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常处理器
 *
 * @author motor
 * @create 2021-01-09-20:03
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    /**
     * 将异常信息存放到Request域中
     */
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model) {
        // 判断异常是否为自定义异常
        if (ex instanceof CustomizeException) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", "服务有点小问题，稍后再来试试吧~");
        }
        // 跳转到error.html
        return new ModelAndView("error");
    }

}
