package com.formssi.mall.flash.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀活动查询对象
 * </p>
 *
 * @author hudemin
 * @since 2022/4/14 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashActQry extends PageQry {

    private String actName;


    private LocalDateTime endTime;

    private LocalDateTime startTime;
}
