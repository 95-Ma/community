package motor.community.controller;

import motor.community.cache.TagCache;
import motor.community.dto.QuestionDTO;
import motor.community.model.Question;
import motor.community.model.User;
import motor.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 发布问题控制器
 *
 * @author motor
 * @create 2021-01-05-14:24
 */
@Controller
public class PublishController {


    @Resource
    private QuestionService questionService;

    /**
     * 编辑控制器
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        // 获取问题
        QuestionDTO question = questionService.getById(id);
        // 将问题相关信息传入Request域中并数据回显
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    /**
     * （对发起内容作非空判断）
     *
     * @param title       标题
     * @param description 内容描述
     * @param tag         标签
     * @param request     原生Request API
     * @param model
     * @return 发起失败返回当前页面，成功则放回首页
     */
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签" + invalid);
            return "publish";
        }

        // 用户未登录时编辑发起问题内容提交后，数据回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        // 获取Session域中的用户信息
        User user = (User) request.getSession().getAttribute("user");

        // 如果用户未登录，提示错误信息
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        // 用户提交的问题相关信息数据交由逻辑层判断是否更新或插入
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        // 调用逻辑层判断执行方法
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
