//package com.youngmok.myboard.dao;
//
//import com.youngmok.myboard.domain.BoardVO;
//import com.youngmok.myboard.domain.SearchCondition;
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
//public class BoardDAOImplTest {
//    @Autowired
//    private BoardDAO BDAO;
//
////    @Test
////    public void countTest() throws Exception {
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////        assertTrue(BDAO.selectCount()==1);
////
////        assertTrue(BDAO.insertBoard(board)==1);
////        assertTrue(BDAO.selectCount()==2);
////    }
////
////    @Test
////    public void insertTest() throws Exception {
////        BDAO.deleteAll();
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////
////        board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////        assertTrue(BDAO.selectCount()==2);
////
////        BDAO.deleteAll();
////        board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////        assertTrue(BDAO.selectCount()==1);
////    }
////
////    @Test
////    public void multipleInsertTest() throws Exception{
////        BDAO.deleteAll();
////
////        for(int i=1;i<=100;i++){
////            BoardVO board = new BoardVO("Test title"+i, "Test content"+i, "kym1000");
////            BDAO.insertBoard(board);
////        }
////        assertTrue(BDAO.selectCount() == 100);
////    }
////
////    @Test
////    public void selectPageTest() throws Exception {
////        BDAO.deleteAll();
////
////        for(int i=1;i<=10;i++){
////            BoardVO board = new BoardVO("Test title"+i, "Test content"+i, "kym1000");
////            BDAO.insertBoard(board);
////        }
////
////        SearchCondition SC = new SearchCondition();
////
////        SC.setPageSize(3);
////
////        List<BoardVO> list = BDAO.searchSelectPage(SC);
////        assertTrue(list.get(0).getTitle().equals("Test title10"));
////        assertTrue(list.get(1).getTitle().equals("Test title9"));
////        assertTrue(list.get(2).getTitle().equals("Test title8"));
////
////        SC.setPageSize(1);
////
////        list = BDAO.searchSelectPage(SC);
////        assertTrue(list.get(0).getTitle().equals("Test title10"));
////    }
////
////    @Test
////    public void searchSelectPageTest() throws Exception {
////        BDAO.deleteAll();
////
////        for(int i=1;i<=20;i++){
////            BoardVO board = new BoardVO("title"+i,"asdadadsad","kym1000"+i);
////            BDAO.insertBoard(board);
////        }
////
////        // 조건 제목으로 검색
////        SearchCondition sc = new SearchCondition(1,10,"title2","T");
////        List<BoardVO> list = BDAO.searchSelectPage(sc);
////        System.out.println("list = " + list);
////        assertTrue(list.size()==2);
////
////        // 조건 작성자로 검색
////        sc = new SearchCondition(1,10,"kym10002","W");
////        list = BDAO.searchSelectPage(sc);
////        System.out.println("list = " + list);
////        assertTrue(list.size()==2);
////
////        BDAO.deleteAll();
////
////        BoardVO board = new BoardVO("title","kym1000","asdf");
////        BDAO.insertBoard(board);
////
////        BoardVO board2 = new BoardVO("kym1000","asdadadsad","asdf");
////        BDAO.insertBoard(board2);
////
////        // 조건 제목,내용로 검색
////        sc = new SearchCondition(1, 10, "kym1000", "");
////        list = BDAO.searchSelectPage(sc);
////        System.out.println("list = " + list);
////        assertTrue(list.size()==2);
////
////    }
////
////
////    @Test
////    public void selectTest() throws Exception {
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////
////        Integer bno = BDAO.selectAll().get(0).getBno();
////        board.setBno(bno);
////        BoardVO board2 = BDAO.selectBoardOne(bno);
////        assertTrue(board.getBno()== board2.getBno());
////    }
////
////    @Test
////    public void selectAllTest() throws Exception {
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        List<BoardVO> list = BDAO.selectAll();
////        assertTrue(list.size() == 0);
////
////        BoardVO boardDto = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(boardDto)==1);
////
////        list = BDAO.selectAll();
////        assertTrue(list.size() == 1);
////
////        assertTrue(BDAO.insertBoard(boardDto)==1);
////        list = BDAO.selectAll();
////        assertTrue(list.size() == 2);
////    }
////
////    @Test
////    public void deleteAllTest() throws Exception {
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        assertTrue(BDAO.insertBoard(board)==1);
////        assertTrue(BDAO.insertBoard(board)==1);
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////    }
////
////    @Test
////    public void deleteTest() throws Exception {
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////        Integer bno = BDAO.selectAll().get(0).getBno();
////        assertTrue(BDAO.boardDelete(bno, board.getWriter())==1);
////        assertTrue(BDAO.selectCount()==0);
////
////        // 삭제 실패 유도
////        assertTrue(BDAO.insertBoard(board)==1);
////        bno = BDAO.selectAll().get(0).getBno();
////        assertTrue(BDAO.boardDelete(bno, board.getWriter()+"다른작성자")==0);
////        assertTrue(BDAO.selectCount()==1);
////    }
////
////    @Test
////    public void updateTest() throws Exception {
////        BDAO.deleteAll();
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////
////        Integer bno = BDAO.selectAll().get(0).getBno();
////        System.out.println("bno = " + bno);
////        board.setBno(bno);
////        board.setTitle("mod title");
////        assertTrue(BDAO.boardUpdate(board)==1);
////
////        BoardVO board2 = BDAO.selectBoardOne(bno);
////        assertTrue(board.getTitle()!=board2.getTitle());
////    }
////
////    @Test
////    public void boardCntUpTest() throws Exception {
////        BDAO.deleteAll();
////        assertTrue(BDAO.selectCount()==0);
////
////        BoardVO board = new BoardVO("Test title", "Test content", "kym1000");
////        assertTrue(BDAO.insertBoard(board)==1);
////        assertTrue(BDAO.selectCount()==1);
////
////        Integer bno = BDAO.selectAll().get(0).getBno();
////        assertTrue(BDAO.increaseViewCntUp(bno)==1);
////
////        board = BDAO.selectBoardOne(bno);
////        assertTrue(board!=null);
////        assertTrue(board.getCnt() == 1);
////
////        assertTrue(BDAO.increaseViewCntUp(bno)==1);
////        board = BDAO.selectBoardOne(bno);
////        assertTrue(board!=null);
////        assertTrue(board.getCnt() == 2);
////    }
//}