package ltd.newbee.mall.core.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@TableName("tb_newbee_mall_user")
@Data
public class MallUserDTO implements Serializable {
    private static final long serialVersionUID = -4514041413899643970L;

    @Size(min = 1, max = 20, message = "手机号在1-20个字符之间", groups = LoginName.class)
    private String loginName;


    @Size(min = 1, max = 20, message = "密码在1-20个字符之间", groups = Password.class)
    private String password;

    @NotBlank(message = "验证码不能为空", groups = VerifyCode.class)
    private String verifyCode;

    public interface LoginName {
    }

    public interface Password {

    }

    public interface VerifyCode {

    }
    /**
     * 顺序分组
     */
    @GroupSequence({LoginName.class, Password.class, VerifyCode.class})
    public interface Register {
    }

    /**
     * 顺序分组
     */
    @GroupSequence({LoginName.class, Password.class, VerifyCode.class})
    public interface Login {
    }
}
