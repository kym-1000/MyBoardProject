//package com.youngmok.myboard.dao;
//
//import com.youngmok.myboard.domain.BoardVO;
//import com.youngmok.myboard.domain.CommentDTO;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class CommentDAOImplTest {
//
//    @Autowired
//    private BoardDAO BDAO;
//
//    @Autowired
//    private CommentDAO CDAO;
//
////    private int testBoard() {
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        BDAO.insertBoard(board);
////        return BDAO.selectAll().get(0).getBno();
////    }
////
////    @Test
////    public void commentInsertTest() {
////        BDAO.deleteAll();
////        CDAO.deleteAll();
////
////        int bno = testBoard();
////
////        CommentDTO comment = new CommentDTO(bno,"kym1000","내용입니다");
////        System.out.println("comment = " + comment);
////        assertTrue(CDAO.commentInsert(comment)==1);
////    }
////
////
////    @Test
////    public void multipleCommentInsertTest() {
////        BDAO.deleteAll();
////        CDAO.deleteAll();
////
////        int bno = testBoard();
////
////        for(int i=1;i<=10;i++){
////            CommentDTO comment = new CommentDTO(bno,"kym1000","내용입니다"+i);
////            CDAO.commentInsert(comment);
////        }
////
////        assertTrue(CDAO.commentCount(bno)==10);
////
////    }
////
////    @Test
////    public void replyCommentTest() {
////        BDAO.deleteAll();
////        CDAO.deleteAll();
////
////        int bno = testBoard();
////
////        CommentDTO comment = new CommentDTO(bno,"kym1000","내용입니다");
////        System.out.println("comment = " + comment);
////        assertTrue(CDAO.commentInsert(comment)==1);
////
////        List<CommentDTO> commentList = CDAO.commentSelectList(bno);
////
////        int pcno = commentList.get(0).getCno();
////        System.out.println(pcno);
////
////        CommentDTO comment2 = new CommentDTO(bno,pcno,"kym1000","대댓글입니다");
////        assertTrue(CDAO.commentInsert(comment2)==1);
////
////    }
////
////    @Test
////    public void commentDeleteTest(){
////        BDAO.deleteAll();
////        CDAO.deleteAll();
////
////        int bno = testBoard();
////
////        CommentDTO comment = new CommentDTO(bno,"kym1000","내용입니다");
////        System.out.println("comment = " + comment);
////        assertTrue(CDAO.commentInsert(comment)==1);
////
////        List<CommentDTO> commentList = CDAO.commentSelectList(bno);
////        int cno = commentList.get(0).getCno();
////        CDAO.deleteComment(cno, bno);
////
////        assertTrue(CDAO.commentSelectList(bno).size()==0);
////
////    }
//
//
//}