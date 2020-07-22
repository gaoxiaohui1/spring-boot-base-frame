package com.base.frame.api.vo;

import com.base.frame.common.utils.ExportAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelVO {
    @ExportAnnotation(name = "城市")
    private String city;
    @ExportAnnotation(index = 1, name = "数量")
    private Integer count;
}
