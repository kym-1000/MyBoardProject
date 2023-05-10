//package com.youngmok.myboard.controller;
//
//import com.youngmok.myboard.dao.UserDAO;
//import com.youngmok.myboard.domain.ProjectFileVO;
//import com.youngmok.myboard.domain.UserVO;
//import com.youngmok.myboard.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class loginControllerTest {
//
//    @Autowired
//    UserDAO UDAO;
//
//    @Autowired
//    UserService US;
//
////    @Test
////    public void loginTest() throws Exception{
////        UDAO.deleteAll();
////
////        List<ProjectFileVO> fileList = new ArrayList<>();
////
////        UserVO user = new UserVO("kym10001", "794256@@q", "김영목", "aaa@aaa.com", new Date(), new Date());
////        assertTrue(US.join(user,fileList));
////        assertTrue(UDAO.userCount()==1);
////
////        UserVO user2 = US.login("kym10001", "794256@@q");
////
////        System.out.println(user2);
////
////        assertTrue(Objects.equals(user2.getId(), user.getId()) && Objects.equals(user2.getPwd(), user.getPwd()));
////
////    }
//
//}