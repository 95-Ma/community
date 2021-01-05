package motor.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 发布问题控制器
 *
 * @author motor
 * @create 2021-01-05-14:24
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
}
