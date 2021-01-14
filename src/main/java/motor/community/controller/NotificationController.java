package motor.community.controller;

import motor.community.dto.NotificationDTO;
import motor.community.enums.NotificationEnum;
import motor.community.enums.NotificationTypeEnum;
import motor.community.mapper.NotificationMapper;
import motor.community.model.User;
import motor.community.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author motor
 * @create 2021-01-12-21:30
 */
@Controller
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id,
                               HttpServletRequest request) {
        // 检查登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
