package com.mmall.service.lmpl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.service.IUserService;
import com.mmall.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServicelmpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);

        if (resultCount == 0) {
            return ServerResponse.createByMessage("用户不存在");
        }

        //todo 密码MD5登录
        User user = userMapper.selectLogin(username, password);
        if (user == null) {
            return ServerResponse.crateBySuccessMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.crateBySuccess("登录成功", user);

    }
}
