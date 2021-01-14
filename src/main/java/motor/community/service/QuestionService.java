package motor.community.service;

import motor.community.dto.QuestionQueryDTO;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;
import motor.community.dto.PaginationDTO;
import motor.community.dto.QuestionDTO;
import motor.community.mapper.QuestionExtMapper;
import motor.community.mapper.QuestionMapper;
import motor.community.mapper.UserMapper;
import motor.community.model.Question;
import motor.community.model.QuestionExample;
import motor.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private QuestionExtMapper questionExtMapper;

    /**
     * 调用持久化层接口方法获取QuestionDTO集合
     *
     * @param page 页数
     * @param size 显示条数
     * @return PaginationDTO
     */
    public PaginationDTO getAllQuestionDTO(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        // 新建页码DTO对象
        PaginationDTO paginationDTO = new PaginationDTO();
        // 根据条件获取问题列表总记录数
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
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
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        // 新建问题DTO列表（包含用户信息）
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        // 遍历问题列表，根据creator查询用户信息并注入问题DTO
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        // 将遍历获取的特定问题列表（包含用户信息）注入paginationDTO
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO getAllQuestionDTO(Long userId, Integer page, Integer size) {
        // 新建页码DTO对象
        PaginationDTO paginationDTO = new PaginationDTO();
        // 获取特定用户的问题列表总记录数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        // 新建问题DTO列表（包含用户信息）
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        // 遍历问题列表，根据creator查询用户信息并注入问题DTO
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        // 将遍历获取的特定问题列表（包含用户信息）注入paginationDTO
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 将传入参数作为形参调用持久层接口方法查询相关数据
     *
     * @param id
     * @return
     */
    public QuestionDTO getById(Long id) {
        // 返回特定问题
        Question question = questionMapper.selectByPrimaryKey(id);
        // 假如查询失败
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        // 将question对象相同属性注入questionDTO对象中
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        // 返回用户信息
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        // 注入用户信息属性到questionDTO对象
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 根据问题id判断是否更新数据库信息或插入数据到数据库
     */
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            // 更新问题
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setCreator(question.getCreator());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExample(updateQuestion, questionExample);
            // 当更新操作失败时
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 将阅读量加一并存放到数据库中
     */
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
