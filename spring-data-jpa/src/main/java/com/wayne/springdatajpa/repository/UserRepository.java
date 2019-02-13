package com.wayne.springdatajpa.repository;

import com.wayne.springdatajpa.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByUserNameOrEmail(String userName, String email);

    /**
     * @Query 上面的 1 代表的是方法参数里面的顺序，如果有多个参数也可以按照这个方式添加 1、2、3....。
     * 除了按照这种方式传参外，还可以使用 @Param 来支持。
     *
     * 如涉及到删除和修改需要加上 @Modifying，
     * 也可以根据需要添加 @Transactional 对事务的支持、操作超时设置等。
     *
     * @param userName
     * @param id
     * @return
     */
    @Transactional(timeout = 10)
    @Modifying
    @Query("update User set userName = ?1 where id = ?2")
    int modifyById(String userName, Long id);

    @Transactional
    @Modifying
    @Query("delete from User where id =?1")
    void deleteById(Long id);


    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);

    @Query("select u from User u")
    Page<User> findAll(Pageable pageable);

    /**
     * Spring Data JPA 已经帮我们内置了分页功能，在查询的方法中，需要传入参数 Pageable，
     * 当查询中有多个参数的时候 Pageable 建议作为最后一个参数传入。
     *
     * Pageable 是 Spring 封装的分页实现类，使用的时候需要传入页数、每页条数和排序规则，
     * Page 是 Spring 封装的分页对象，封装了总页数、分页数据等。
     * 回对象除使用 Page 外，还可以使用 Slice 作为返回值。
     * @param nickName
     * @param pageable
     * @return
     */
    Page<User> findByNickName(String nickName, Pageable pageable);

    Slice<User> findByNickNameAndEmail(String nickName, String email, Pageable pageable);
}
