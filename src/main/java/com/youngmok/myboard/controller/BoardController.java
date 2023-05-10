package com.youngmok.myboard.controller;

import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.SearchCondition;
//import com.youngmok.myboard.handler.AzureFileHandler;
import com.youngmok.myboard.handler.FileHandler;
import com.youngmok.myboard.handler.PageHandler;
import com.youngmok.myboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FileHandler FH;

//    @Autowired
//    private AzureFileHandler FH;


    // 보드 리스트를 불러오는 메서드
    @GetMapping("/list")
    public String board(SearchCondition sc, Model m, HttpServletRequest request){

        try {
            int totalCnt = BS.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardVO> list = BS.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "board/boardList";
    }

    // 보드 리스트에서 상세페이지를 눌러 해당 페이지를 확인하는 메서드
    @GetMapping("/read")
    public String read(Integer bno, Model m, Integer page, Integer pageSize, SearchCondition sc){

        try {
            BoardDTO board = BS.boardread(bno);
            System.out.println("board = " + board);
            if(board.getFList().size() != 0) {
//                System.out.println("\"flist : \"+bdto.getFList().get(0).toString() = " + "flist : " + board.getFList().get(0).toString());
                m.addAttribute("fList",board.getFList());
            }
            m.addAttribute("Board",board);
            m.addAttribute("page",page);
            m.addAttribute("pageSize", pageSize);
            m.addAttribute("searchCondition",sc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board/boardDetail";
    }

    //새로운 글쓰기 메서드
    @GetMapping("/write")
    public String boardwrite(Model model) {

        model.addAttribute("mode","new");

        return "board/boardDetail";
    }

    // 글로 쓴 내용을 db에 등록하는 메서드
    @PostMapping("/write")
    public String boardwrite(String title, String content, HttpSession session,Model m,RedirectAttributes rattr, @RequestParam(name="files",required = false) MultipartFile[] files) {
        String writer = (String)session.getAttribute("id");
        BoardVO board = new BoardVO(title,content,writer);
        System.out.println("board = " + board);
        int isOk = 0;

        try {
            List<ProjectFileVO> fList = null;
            if(files[0].getSize() >0) { // 값이 있는지 체크
                System.out.println("files.toString() = " + files.toString());
                fList = FH.uploadFiles(files); // 핸들러에 있는 메서드로 실제 파일들의 정보를 fList에 담아줌
                isOk = BS.boardadd(new BoardDTO(board,fList));
            } else{
                isOk = BS.boardadd(new BoardDTO(board));
            }

            if(isOk>0){
                System.out.println("\"게시글 등록 여부\" = " + "게시글 등록 성공!");
            } else{
                System.out.println("\"게시글 등록 여부\" = " + "게시글 등록 실패!");
            }
            rattr.addFlashAttribute("msg", "WRT_OK");
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(board);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "WRT_ERR");
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
        String writer = (String)session.getAttribute("id");

        try {
            int isOk = BS.deleteOne(bno,writer);
            if(isOk>0){
                System.out.println("\"게시글 삭제 여부\" = " + "게시글 삭제 성공!");
            } else{
                System.out.println("\"게시글 삭제 여부\" = " + "게시글 삭제 실패!");
            }
            rattr.addFlashAttribute("msg", "DEL_OK");
            m.addAttribute("SearchCondition", sc);
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "DEL_ERR");
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
            if(files[0].getSize() >0) { // 값이 있는지 체크
                System.out.println("files.toString() = " + files.toString());
                fList = FH.uploadFiles(files); // 핸들러에 있는 메서드로 실제 파일들의 정보를 fList에 담아줌
                isOk = BS.modifyBoard(new BoardDTO(board,fList));
            } else{
                isOk = BS.modifyOne(board);
            }

            if(isOk>0){
                System.out.println("\"게시글 수정 여부\" = " + "게시글 수정 성공!");
            } else{
                System.out.println("\"게시글 수정 여부\" = " + "게시글 수정 실패!");
            }
            rattr.addFlashAttribute("msg", "MOD_OK");
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(board);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "WRT_ERR");
        }


        return "redirect:/board/list";
    }


}
