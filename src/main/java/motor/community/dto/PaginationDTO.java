package motor.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 页码数据传输对象 包含问题列表和分页信息
 *
 * @author motor
 * @create 2021-01-06-16:01
 */
@Data
public class PaginationDTO {
    // 展示问题列表
    private List<QuestionDTO> questions;
    // 展示前一页
    private boolean showPrevious;
    // 展示首页
    private boolean showFirstPage;
    // 展示下一页
    private boolean showNext;
    // 展示尾页
    private boolean showEndPage;
    // 当前页码
    private Integer page;
    // 页码数组
    private List<Integer> pages = new ArrayList<>();
    // 总页数
    private Integer totalPage;

    /**
     * 根据总记录数和每页条数获取总页数
     *
     * @param totalCount 总记录数
     * @param size       每页条数
     * @return
     */
    public Integer getTotalPageByCount(Integer totalCount, Integer size) {
        // 利用总记录数求余每页条数判断总页数
        if (totalCount % size == 0) {
            return totalPage = totalCount / size;
        } else {
            return totalPage = (totalCount / size) + 1;
        }
    }

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        // 为总页数赋值
        this.totalPage = getTotalPageByCount(totalCount, size);

        // 为当前页码赋值
        this.page = page;

        // 填充页码数组
        // 首先填充当前页面
        pages.add(page);
        // 数组最多为7个页码（向前遍历三个数据，合法的话添加到数组头部；向后遍历三个数组，合法的话添加到数组尾部）
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) pages.add(0, page - i);
            if (page + i <= totalPage) pages.add(page + i);
        }

        // 跳转上一页的展示与否
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        // 跳转下一页的展示与否
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        // 跳转首页的展示与否
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        // 跳转尾页的展示与否
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
