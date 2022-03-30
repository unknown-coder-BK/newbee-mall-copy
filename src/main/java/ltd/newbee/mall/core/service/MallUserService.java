package ltd.newbee.mall.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.core.entity.MallUser;

public interface MallUserService extends IService<MallUser> {
    /**
     * 用户注册
     *
     * @param loginName 用户名
     * @param password  密码
     * @return boolean
     */
    boolean register(String loginName, String password);
}
