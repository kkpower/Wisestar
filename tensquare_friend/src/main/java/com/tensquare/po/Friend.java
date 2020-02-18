package com.tensquare.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName Friend
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/10
 **/
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
@Data
public class Friend implements Serializable {
    @Id
    private String userid;

    @Id
    private String friendid;

    private String islike;
}
