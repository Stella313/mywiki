package com.wiki.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiki.mywiki.domain.User;
import com.wiki.mywiki.domain.UserExample;
import com.wiki.mywiki.exception.BusinessException;
import com.wiki.mywiki.exception.BusinessExceptionCode;
import com.wiki.mywiki.mapper.UserMapper;
import com.wiki.mywiki.req.UserLoginReq;
import com.wiki.mywiki.req.UserQueryReq;
import com.wiki.mywiki.req.UserResetPasswordReq;
import com.wiki.mywiki.req.UserSaveReq;
import com.wiki.mywiki.resp.UserLoginResp;
import com.wiki.mywiki.resp.UserQueryResp;
import com.wiki.mywiki.resp.PageResp;
import com.wiki.mywiki.util.CopyUtil;
import com.wiki.mywiki.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper userMapper;
    private UuidUtils uuidUtils;
    public List<User> list(){
        return userMapper.selectByExample(null);
    }
    public PageResp<UserQueryResp> list(UserQueryReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        // 只对下面遇到的第一个sql起作用
        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList =  userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(UserSaveReq req){
        User user = CopyUtil.copy(req, User.class);
        if(ObjectUtils.isEmpty(req.getId())){
            if(selectByLoginName(req.getLoginName()) == null){
                // 新增
                user.setId(uuidUtils.getId());
                userMapper.insert(user);
            }else{
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }else{
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }else{
            return userList.get(0);
        }
    }

    /**
     * 重置密码
     */
    public void resetPassword(UserResetPasswordReq req){
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }
    /**
     * 登录
     */
    public UserLoginResp login(UserLoginReq req){
        User userDb = selectByLoginName(req.getLoginName());
        if(ObjectUtils.isEmpty(userDb)){
            // 用户名不存在
            LOG.info("用户名不存在，{}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else{
            if(userDb.getPassword().equals(req.getPassword())){
                // 登录成功
                UserLoginResp userLoginResp = CopyUtil.copy(userDb, UserLoginResp.class);
                return userLoginResp;
            }else{
                // 密码不对
                LOG.info("密码不对，输入密码：{}, 数据库密码：{}", req.getLoginName(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}
