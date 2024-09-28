package com.example.houmi_backend.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.Callable;
@Data
public class QueryTeamPageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1647451568520517392L;
    /**
     * 队伍名字
     */
    private String name;

    /**
     * 0-公开 1-私密 2-加密
     */
    private Integer status;
    /**
     * 搜索文本
     */
    private String searchText;
}
