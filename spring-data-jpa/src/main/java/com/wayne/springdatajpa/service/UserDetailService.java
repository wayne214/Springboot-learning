package com.wayne.springdatajpa.service;

import com.wayne.springdatajpa.model.UserDetail;
import com.wayne.springdatajpa.param.UserDetailParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDetailService {
    public Page<UserDetail> finbByCondition(UserDetailParam detailParam, Pageable pageable);
}
