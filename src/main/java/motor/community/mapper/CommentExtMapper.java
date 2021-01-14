package motor.community.mapper;

import motor.community.model.Comment;
import motor.community.model.CommentExample;
import motor.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incComment(Comment comment);
}