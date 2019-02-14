package com.wayne.springdatajpa.Test;

import com.wayne.springdatajpa.model.UserDetail;
import com.wayne.springdatajpa.model.UserInfo;
import com.wayne.springdatajpa.param.UserDetailParam;
import com.wayne.springdatajpa.repository.UserDetailRepository;
import com.wayne.springdatajpa.service.UserDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaSpecificationTests {

    @Resource
    private UserDetailService userDetailService;
    @Resource
    private UserDetailRepository userDetailRepository;

    @Test
    public void testFindByCondition() {
        int page = 0,size = 10;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page,size, sort);
        UserDetailParam param = new UserDetailParam();
        param.setIntroduction("程序员");
        param.setMinAge(10);
        param.setMaxAge(30);
        Page<UserDetail> page1 = userDetailService.finbByCondition(param,pageable);
        for(UserDetail userDetail: page1) {
            System.out.println("userDetail: "+ userDetail.toString());
        }
    }

    @Test
    public void testUserInfo()  {
        List<UserInfo> userInfos=userDetailRepository.findUserInfo("钓鱼");
        for (UserInfo userInfo:userInfos){
            System.out.println("userInfo: "+userInfo.getUserName()+"-"+userInfo.getEmail()+"-"+userInfo.getHobby()+"-"+userInfo.getIntroduction());
        }
    }
}
