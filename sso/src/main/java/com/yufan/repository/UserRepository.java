package com.yufan.repository;

import com.yufan.bean.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    public User findUserByUsernameAndPassword(String username,String password);

    public User findUserByUsername(String username);

    public User findUserByPhone(String phone);

    public User findUserByEmail(String email);


}
