package motor.community.servie;

import motor.community.dto.PaginationDTO;
import motor.community.dto.QuestionDTO;
import motor.community.mapper.QuestionMapper;
import motor.community.mapper.UserMapper;
import motor.community.model.Question;
import motor.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 问题列表展示业务逻辑
 *
 * @author motor
 * @create 2021-01-05-21:24
 */
@Service
public class QuestionService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    /**
     * 调用持久化层接口方法获取QuestionDTO集合
     * @param page 页数
     * @param size 显示条数
     * @return PaginationDTO
     */
    public PaginationDTO getAllQuestionDTO(Integer page, Integer size) {
        // 新建页码DTO对象
        PaginationDTO paginationDTO = new PaginationDTO();
        // 获取问题列表总记录数
        Integer totalCount = questionMapper.questionCount();
        // 对传入参数page的简单限制
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPageByCount(totalCount, size)) {
            page = paginationDTO.getTotalPageByCount(totalCount, size);
        }
        // 根据总记录数，页数和显示条数获取paginationDTO
        paginationDTO.setPagination(totalCount, page, size);


        // 偏移位置
        Integer offset = size * (page - 1);
        // 获取问题列表（不含用户信息）
        List<Question> questions = questionMapper.getAllQuestion(offset, size);
        // 新建问题DTO列表（包含用户信息）
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        // 遍历问题列表，根据creator查询用户信息并注入问题DTO
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        // 将遍历获取的特定问题列表（包含用户信息）注入paginationDTO
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO getAllQuestionDTO(Integer userId, Integer page, Integer size) {
        // 新建页码DTO对象
        PaginationDTO paginationDTO = new PaginationDTO();
        // 获取特定用户的问题列表总记录数
        Integer totalCount = questionMapper.questionCountByUserId(userId);
        // 对传入参数page的简单限制
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPageByCount(totalCount, size)) {
            page = paginationDTO.getTotalPageByCount(totalCount, size);
        }
        // 根据总记录数，页数和显示条数获取paginationDTO
        paginationDTO.setPagination(totalCount, page, size);


        // 偏移位置
        Integer offset = size * (page - 1);
        // 获取问题列表（不含用户信息）
        List<Question> questions = questionMapper.getAllQuestionByUserId(userId, offset, size);
        // 新建问题DTO列表（包含用户信息）
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        // 遍历问题列表，根据creator查询用户信息并注入问题DTO
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        // 将遍历获取的特定问题列表（包含用户信息）注入paginationDTO
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    /**
     * 将传入参数作为形参调用持久层接口方法查询相关数据
     *
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        // 返回特定问题
        Question question = questionMapper.getById(id);
        // 将question对象相同属性注入questionDTO对象中
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        // 返回用户信息
        User user = userMapper.findById(question.getCreator());
        // 注入用户信息属性到questionDTO对象
        questionDTO.setUser(user);
        return questionDTO;
    }
}
