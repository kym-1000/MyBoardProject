//package com.youngmok.myboard.service;
//
//import com.youngmok.myboard.dao.UserDAO;
//import com.youngmok.myboard.domain.ProjectFileVO;
//import com.youngmok.myboard.domain.UserVO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class UserServiceImplTest {
//
//    @Autowired
//    private UserService BS;
//
//    @Autowired
//    private UserDAO UDAO;
//
//
////    @Test
////    public void modifyUser() throws Exception {
////        UDAO.deleteAll();
////        assertTrue(UDAO.userCount() == 0);
////
////        UserVO user = new UserVO("kym10001", "794256@@q", "김영목", "aaa@aaa.com", new Date(), new Date());
////        assertTrue(UDAO.insertUser(user)==1);
////        assertTrue(UDAO.userCount()==1);
////
////        UserVO user2 = new UserVO("kym10001", "123456@q", "홍길동", "bbb@bbb.com", new Date(), new Date());
////
////        List<ProjectFileVO> flist = new ArrayList<ProjectFileVO>();
////
////        assertTrue(BS.modify(user2,flist));
////        assertTrue(UDAO.userCount()==1);
////
////    }
//
//}