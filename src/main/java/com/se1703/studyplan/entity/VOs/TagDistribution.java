package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author leekejin
 * @date 2020/9/17 22:01
 **/
@Data
public class TagDistribution {
    @ApiModelProperty(value = "标签名")
    private String tagName;

    @ApiModelProperty(value = "用时，单位分钟")
    private Integer minute;

}
