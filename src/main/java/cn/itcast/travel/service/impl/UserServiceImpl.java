package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl  implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {

        User u = userDao.findByUserName(user.getUsername());

        if(u != null){


            return false;
        }
        //设置激活码
        user.setCode(UuidUtil.getUuid());
        //设置激活状态

        user.setStatus("N");



        userDao.save(user);

        //发送邮件
        String contnt="<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活[黑马旅游] </a>" +
                "或者复制http://localhost:8080/travel/user/active?code="+user.getCode()+"进行激活";

        MailUtils.sendMail(user.getEmail(),contnt,"激活邮件");

        return true;

    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {

        //根据激活码查询用户
         User user =  userDao.findByCode(code);

         if(user!=null){

             userDao.updateStatus(user);
             return true;

         }else {

             return false;
         }

    }

    @Override
    public User login(User user) {

        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
