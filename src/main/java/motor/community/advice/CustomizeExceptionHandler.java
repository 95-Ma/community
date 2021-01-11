package motor.community.advice;

import motor.community.dto.ResultDTO;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * 判断传入数据是否为json格式，是的话返回json提示数据，否则输出不同提示信息并跳转到错误页面
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(HttpServletRequest request, Throwable ex, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            // 返回json数据
            // 判断异常是否为自定义异常
            if (ex instanceof CustomizeException) {
                return ResultDTO.errorOf((CustomizeException) ex);
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
        } else {
            // 判断异常是否为自定义异常
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            // 跳转到error.html
            return new ModelAndView("error");
        }
    }
}
