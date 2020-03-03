package com.ke.switchlinkafrica.transactionprocessing.repositories;


import com.ke.switchlinkafrica.transactionprocessing.entities.UserData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<UserData, BigInteger> {

    public UserData findByUserNameAndEnabled(String userName, int enabled);


    public List<UserData> findAllByEnabled(short enabled);

    Page<UserData> findAll(Pageable pg);

    public UserData findById(long id);


    public void deleteById(long id);
}
