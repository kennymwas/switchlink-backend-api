package com.ke.switchlinkafrica.transactionprocessing.services;

import com.ke.switchlinkafrica.transactionprocessing.entities.UserData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserService {

    UserData save(UserData user);

    public UserData getUser(String userName, int enabled);


    public Page<UserData> findAll(Pageable pg);

    void delete(long id);
}
