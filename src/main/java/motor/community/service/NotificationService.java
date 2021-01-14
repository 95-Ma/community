package motor.community.service;

import com.sun.javaws.exceptions.ErrorCodeResponseException;
import motor.community.dto.NotificationDTO;
import motor.community.dto.PaginationDTO;
import motor.community.dto.QuestionDTO;
import motor.community.enums.NotificationEnum;
import motor.community.enums.NotificationTypeEnum;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;
import motor.community.mapper.NotificationMapper;
import motor.community.mapper.UserMapper;
import motor.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author motor
 * @create 2021-01-12-16:06
 */
@Service
public class NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private UserMapper userMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {
        // 新建页码DTO对象
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        // 获取特定用户的问题列表总记录数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
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
        // 获取通知列表列表（
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        // 如果通知列表为空，则返回空集合
        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        // 将通知DTO列表注入paginationDTO
        paginationDTO.setData(notificationDTOS);


        return paginationDTO;
    }

    /**
     * 计算未读通知舒利昂
     */
    public Long unreadCount(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationTypeEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }


    /**
     * 读取未读通知
     */
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationTypeEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
