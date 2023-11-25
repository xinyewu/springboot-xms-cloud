package com.yc.bean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resorder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roid;
    private Integer userid;
    private String address;
    private String tel;

    private String ordertime;
    private String deliverytime;
    private String ps;
    private Integer status ;

}
