package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @Description TODO
 * @classNameTurntAble
 * @Date 2023/2/23 14:54
 * @Version 1.0
 **/
@DbName(name = "gameroomitemdb")
@Data
@Table(name = "Turntable_TurntableConfig")
@BindID(name = "id")
public class TurnTable extends BaseModel {
    @SeqID(name = "id")
    private Integer id;
    private Integer turntable_index;
    private Integer award;
    private Integer pro;
    private Long type;
}
