//package com.youngmok.myboard.dao;
//
//import com.youngmok.myboard.domain.UserVO;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class UserDAOImplTest {
//
//    @Autowired
//    private UserDAO UDAO;
//
//    Calendar Calendar;
//
////    @Before // 각 테스트가 수행되기 직전에 이 메서드가 실행된다.
////    public void init() {
//////        생일 세팅
////        Calendar = java.util.Calendar.getInstance();
////        Calendar.clear();
////        Calendar.set(1994, 5, 25);
////    }
////
////    @Test(expected = Exception.class) // 예외가 발생해야 테스트 통과
////    public void insertUser() throws Exception {
////        UDAO.deleteAll();
////        assertTrue(UDAO.userCount()==0);
////
////        UserVO user = new UserVO("kym10001", "794256@@q", "김영목", "aaa@aaa.com", new Date(Calendar.getTimeInMillis()), new Date());
////        assertTrue(UDAO.insertUser(user)==1);
////        assertTrue(UDAO.userCount()==1);
////
////        UserVO user2 = new UserVO("kym10002", "794256@@q", "김영목", "aaa@aaa.com", new Date(Calendar.getTimeInMillis()), new Date());
////        assertTrue(UDAO.insertUser(user2)==1);
////        assertTrue(UDAO.userCount()==2);
////
////        // 같은 데이터를 2번 입력하고 예외가 발생하는지 테스트
////        UDAO.insertUser(user);
////    }
////
////    @Test
////    public void selectUser() throws Exception {
////        UDAO.deleteAll();
////        assertTrue(UDAO.userCount()==0);
////
////        UserVO user = new UserVO("kym10001", "794256@@q", "김영목", "aaa@aaa.com", new Date(Calendar.getTimeInMillis()), new Date());
////        assertTrue(UDAO.insertUser(user)==1);
////        assertTrue(UDAO.userCount()==1);
////
////        UserVO user2= UDAO.selectUser("aaaaaaa");
////        assertTrue(user2==null);
////    }
////
////    @Test
////    public void deleteUser() throws Exception {
////        UDAO.deleteAll();
////        assertTrue(UDAO.userCount()==0);
////
////        UserVO user = new UserVO("kym10001", "794256@@q", "김영목", "aaa@aaa.com", new Date(Calendar.getTimeInMillis()), new Date());
////        assertTrue(UDAO.insertUser(user)==1);
////        assertTrue(UDAO.userCount()==1);
////
////        assertTrue(UDAO.deleteUser(user.getId())==1);
////
////        user = UDAO.selectUser("kym10001");
////        assertTrue(user==null);
////        assertTrue(UDAO.userCount()==0);
////    }
//
//}