package entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/18
 **/
@Data
public class PageResult<T> {

    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
