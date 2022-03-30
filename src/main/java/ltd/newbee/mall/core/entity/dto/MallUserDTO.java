package ltd.newbee.mall.core.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_newbee_mall_user")
@Data
public class MallUserDTO implements Serializable {
    private static final long serialVersionUID = -4514041413899643970L;

    private String loginName;

    private String verifyCode;

    private String password;
}
