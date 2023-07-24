package com.youngmok.myboard.controller;

import com.youngmok.myboard.domain.*;
//import com.youngmok.myboard.handler.FileHandler;
import com.youngmok.myboard.handler.AzureFileHandler;
import com.youngmok.myboard.handler.PageHandler;
import com.youngmok.myboard.service.BoardService;
import com.youngmok.myboard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService BS;

    @Autowired
    CommentService CS;

//    @Autowired
//    private FileHandler FH;

    @Autowired
    private AzureFileHandler FH;


    // 보드 리스트를 불러오는 메서드
    @GetMapping("/list")
    public String board(SearchCondition sc, Model m, HttpServletRequest request){

        System.out.println("sc = " + sc);

        try {
            int totalCnt = BS.getSearchResultCnt(sc);  // 총 게시글 수를 가져온다.
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc); // 총게시글과 검색어를(있다면) 이용하여 파일 핸들러 세팅
            List<BoardVO> list = BS.getSearchResultPage(sc);  // 검색어에 맞는 페이지 리스트를 가져온다.
            List<BoardVO> noticeList = BS.getNoticeList();
            System.out.println("list = " + list);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);
            m.addAttribute("noticeList",noticeList);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();

            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "BOARD_LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "board/boardList";
    }

    // 보드 리스트에서 상세페이지를 눌러 해당 페이지를 확인하는 메서드
    @GetMapping("/read")
    public String read(Integer bno, Model m, Integer page, Integer pageSize, SearchCondition sc){

        try {
            BoardDTO board = BS.boardread(bno);   // 해당 bno에 해당하는 게시글을 가져온다.
            System.out.println("board = " + board);
            if(board.getFList().size() != 0) {  // 게시글에 파일이 있다면
                m.addAttribute("fList",board.getFList());  // 파일을 보내준다.
            }
            m.addAttribute("Board",board);
            m.addAttribute("page",page);
            m.addAttribute("pageSize", pageSize);
            m.addAttribute("searchCondition",sc);

            if(board.getBoard().getNotice()==0){ // 공지사항일 경우
                m.addAttribute("notice",true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board/boardDetail";
    }

    //새로운 글쓰기 메서드
    @GetMapping("/write")
    public String boardwrite(Model m) {

        m.addAttribute("mode","new");  // 새로운 글쓰기 상태

        return "board/boardDetail";
    }

    // 글로 쓴 내용을 db에 등록하는 메서드
    @PostMapping("/write")
    public String boardwrite(String title, String content, HttpSession session,Model m,RedirectAttributes rattr, @RequestParam(name="files",required = false) MultipartFile[] files) {
        String writer = (String)session.getAttribute("id");

        int authority = (int) session.getAttribute("authority");

        System.out.println("authority = " + authority);

        if(writer.equals("")){
            m.addAttribute("msg", "SESSION_ERR");
            return "redirect:/";
        }

        BoardVO board = new BoardVO(title,content,writer);
        board.setNotice(authority);   // 관리자가 쓴 공지사항인지 아닌지
        System.out.println("board = " + board);
        int isOk = 0;

        try {
            List<ProjectFileVO> fList = null; 
            if(files[0].getSize() >0) { // 값이 있는지 체크
                System.out.println("files.toString() = " + files.toString());
                fList = FH.uploadFiles(files); // 핸들러에 있는 메서드로 실제 파일들의 정보를 fList에 담아줌
                isOk = BS.boardadd(new BoardDTO(board,fList));  
            } else{
                isOk = BS.boardadd(new BoardDTO(board));  // 파일이 존재하지 않을 경우 게시글만 등록
            }

            if(isOk>0){
                System.out.println("\"게시글 등록 여부\" = " + "게시글 등록 성공!");
            } else{
                System.out.println("\"게시글 등록 여부\" = " + "게시글 등록 실패!");
            }
            rattr.addFlashAttribute("msg", "BOARD_WRT_OK");
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(board);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "BOARD_WRT_ERR");
        }

        return "redirect:/board/list";
    }

    @PostMapping("detail")
    public  String boardDetail(){

        return "board/boardDetail";
    }

    // 게시글을 삭제하는 메서드
    @PostMapping ("boardDelete")
    public  String boardDelete(SearchCondition sc, Integer bno, HttpSession session, RedirectAttributes rattr, Model m){
        String id = (String)session.getAttribute("id");

        try {
            int isOk = BS.deleteOne(bno,id); // 해당 bno와 작성자가 맞는 게시글을 삭제  삭제 시 서비스단에서 댓글과 파일도 삭제
            if(isOk>0) {
                System.out.println("게시판 삭제 및 댓글 삭제 완료");
            }
            rattr.addFlashAttribute("msg", "BOARD_DEL_OK");
            m.addAttribute("SearchCondition", sc);
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "BOARD_DEL_ERR");
        }

        return "redirect:/board/list?page="+sc.getPage()+"&pageSize="+sc.getPageSize();
    }

    // 게시글을 수정하는 메서드
    @PostMapping("modify")
    public String boardModify(BoardVO board, SearchCondition sc, Model m, HttpSession session,@RequestParam(name="files",required = false) MultipartFile[] files,RedirectAttributes rattr){
        String id = (String)session.getAttribute("id");
        board.setWriter(id);

//        ProjectFileVO file = FDAO.selectFileImage(id);

        System.out.println("board = " + board);

        int isOk;

        try {
            List<ProjectFileVO> fList = null;
            if(files[0].getSize() > 0) { // 값이 있는지 체크

                fList = FH.uploadFiles(files); // 핸들러에 있는 메서드로 실제 파일들의 정보를 fList에 담아줌
                System.out.println("fList = " + fList);
                isOk = BS.modifyBoard(new BoardDTO(board,fList));
            } else{
                isOk = BS.modifyOne(board); // 게시글만 존재 할 경우 게시글만 수정
            }

            if(isOk>0){
                System.out.println("\"게시글 수정 여부\" = " + "게시글 수정 성공!");
                rattr.addFlashAttribute("msg", "BOARD_MOD_OK");
            } else {
                System.out.println("\"게시글 수정 여부\" = " + "게시글 수정 실패!");
            }
        } catch (Exception e) {

            e.printStackTrace();
            m.addAttribute(board);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "BOARD_WRT_ERR");
        }


        return "redirect:/board/list";
    }


    @GetMapping("/like")
    public ResponseEntity<String> like(@RequestParam("like") Integer like) {
        try {
            int isOk = BS.boardlike(like);

            if(isOk > 0) {
                System.out.println("\"게시글 추천 성공 여부\" = " + "게시글 추천 성공");
            } else {
                System.out.println("\"게시글 추천 성공 여부\" = " + "게시글 추천 실패");
            }

            return ResponseEntity.ok("Recommendation recorded for like: " + like);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the request");
        }
    }


}
