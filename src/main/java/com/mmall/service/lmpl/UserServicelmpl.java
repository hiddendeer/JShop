package com.mmall.service.lmpl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.service.IUserService;
import com.mmall.pojo.User;
import com.mmall.util.MD5Util;
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
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        //todo 密码MD5登录
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.crateBySuccessMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.crateBySuccess("登录成功", user);

    }

    public ServerResponse<User> register(User user) {
        int resultCount = userMapper.checkUsername(user.getUsername());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }

        resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已存在");
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        resultCount = userMapper.insert(user);

        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }

        return ServerResponse.crateBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkVail(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            //开始检查
            if (Const.USERNAME.equals(str)) {
                int resultCount = userMapper.checkUsername(str);

            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return null;
    }
}
