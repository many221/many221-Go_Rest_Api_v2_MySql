package com.gr_20.Api.repositories;

import com.gr_20.Api.models.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel,Integer> {
}
