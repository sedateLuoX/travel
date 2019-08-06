package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     *根据用户查询用户信息
     *
     * @param username
     * @return
     */
    public User findByUserName( String username);

    public void  save( User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
