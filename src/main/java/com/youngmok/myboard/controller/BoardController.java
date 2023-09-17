package com.youngmok.myboard.controller;

import com.youngmok.myboard.domain.BoardDTO;
import com.youngmok.myboard.domain.BoardVO;
import com.youngmok.myboard.domain.ProjectFileVO;
import com.youngmok.myboard.domain.SearchCondition;
import com.youngmok.myboard.handler.AzureFileHandler;
import com.youngmok.myboard.handler.PageHandler;
import com.youngmok.myboard.service.BoardService;
import com.youngmok.myboard.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final BoardService BS;

//    private final CommentService CS;

    private final AzureFileHandler FH;

//    @Autowired
//    private final FileHandler FH;

    @Autowired // 생성자를 통하여 필드주입
    public BoardController(BoardService boardService,CommentService commentService,AzureFileHandler azureFileHandler) {
        this.BS = boardService;
        this.FH = azureFileHandler;
//        this.CS = commentService;
    }

    // 보드 리스트를 불러오는 메서드
    @GetMapping("/list")
    public String board(SearchCondition sc, Model m, HttpServletRequest request) {
        try {
            int totalCnt = BS.getSearchResultCnt(sc);  // 총 게시글 수를 가져온다.
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc); // 총게시글과 검색어를(있다면) 이용하여 파일 핸들러 세팅
            List<BoardDTO> list = BS.getSearchResultPage(sc);  // 검색어에 맞는 페이지 리스트를 가져온다.
            logger.info(" : "+list);
            List<BoardVO> noticeList = BS.getNoticeList();  // 공지사항 리스트

            logger.info("list : "+list);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);
            m.addAttribute("noticeList", noticeList);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();

            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            logger.error("게시글목록을 불러오지 못하였습니다. : {}", e.getMessage(), e);
            m.addAttribute("msg", "BOARD_LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "board/boardList";
    }

    // 보드 리스트에서 상세페이지를 눌러 해당 페이지를 확인하는 메서드
    @GetMapping("/read")
    public String read(Integer bno, Model m, Integer page, Integer pageSize, SearchCondition sc) {

        try {
            BoardDTO board = BS.boardread(bno);   // 해당 bno에 해당하는 게시글을 가져온다.
            logger.info("board : "+board);
            logger.info("getFile "+board.getFile());
            if (board.getFile() != null ) {  // 게시글에 파일이 있다면
                m.addAttribute("imgFile", board.getFile());  // 파일을 보내준다.
            }

            m.addAttribute("Board", board.getBoard());
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            m.addAttribute("searchCondition", sc);
            if (board.getBoard().getNotice() == 0) { // 공지사항일 경우
                m.addAttribute("notice", true);
            }
        } catch (Exception e) {
            logger.error("게시글을 불러오지 못하였습니다. : {}", e.getMessage(), e);
            m.addAttribute("msg", "BOARD_READ_ERR");
        }
        return "board/boardDetail";
    }

    //새로운 글쓰기 메서드
    @GetMapping("/write")
    public String boardwrite(Model m) {
        m.addAttribute("mode", "new");  // 새로운 글쓰기 상태
        return "board/boardDetail";
    }

    // 글로 쓴 내용을 db에 등록하는 메서드
    @PostMapping("/write")
    public String boardwrite(String title, String content, HttpSession session, Model m, RedirectAttributes rattr, @RequestParam(name = "files", required = false) MultipartFile[] files) {
        String writer = (String) session.getAttribute("id");
        int authority = (int) session.getAttribute("authority"); // 권환 확인

        if (writer == null || writer.equals("")) { // 로그인이 풀렸을경우
            m.addAttribute("msg", "SESSION_ERR");
            return "redirect:/";
        }

        BoardVO board = new BoardVO(title, content, writer);
        board.setNotice(authority);   // 관리자가 쓴 공지사항인지 아닌지
        logger.info("board : "+board);
        int isOk = 0;

        try {
            ProjectFileVO imgFile = null;
            if (files[0].getSize() > 0) { // 값이 있는지 체크
                logger.info( "files : "+Arrays.toString(files));
                imgFile = FH.uploadFiles(files); // 핸들러에 있는 메서드로 실제 파일들의 정보를 imgFile에 담아줌
                isOk = BS.boardadd(new BoardDTO(board, imgFile));
            } else {
                isOk = BS.boardadd(new BoardDTO(board));  // 파일이 존재하지 않을 경우 게시글만 등록
            }

            if (isOk > 0) {
                logger.info("isOk : "+isOk);
                logger.info("게시글 등록 성공!");
            } else {
                logger.warn("isOk : "+isOk);
                throw new Exception();
            }
            rattr.addFlashAttribute("msg", "BOARD_WRT_OK");
        } catch (Exception e) {
            logger.error("게시글 등록을 실패하였습니다. : {}", e.getMessage(), e);
            m.addAttribute(board);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "BOARD_WRT_ERR");
        }

        return "redirect:/board/list";
    }

    // 게시글 상세페이지로 이동
    @PostMapping("detail")
    public String boardDetail() {
        return "board/boardDetail";
    }

    // 게시글을 삭제하는 메서드
    @PostMapping("boardDelete")
    public String boardDelete(SearchCondition sc, Integer bno, HttpSession session, RedirectAttributes rattr, Model m) {
        String id = (String) session.getAttribute("id");

        try {
            int isOk = BS.deleteOne(bno, id); // 해당 bno와 작성자가 맞는 게시글을 삭제  삭제 시 서비스단에서 댓글과 파일도 삭제
            if (isOk > 0) {
                logger.info("게시글(이미지파일) 삭제 및 댓글삭제 완료");
            }
            rattr.addFlashAttribute("msg", "BOARD_DEL_OK");
            m.addAttribute("SearchCondition", sc);
        } catch (Exception e) {
            logger.error("게시글 삭제를 실패하였습니다. : {}", e.getMessage(), e);
            rattr.addFlashAttribute("msg", "BOARD_DEL_ERR");
        }
        return "redirect:/board/list?page=" + sc.getPage() + "&pageSize=" + sc.getPageSize(); // 상세게시글 클릭 전으로 돌아감
    }

    // 게시글을 수정하는 메서드
    @PostMapping("modify")
    public String boardModify(BoardVO board, SearchCondition sc, Model m, HttpSession session, @RequestParam(name = "files", required = false) MultipartFile[] files, RedirectAttributes rattr) {
        String id = (String) session.getAttribute("id"); 
        board.setWriter(id); // 세션에 있는 id를 글쓴이로 등록

//        ProjectFileVO imgFile = FDAO.selectFileImage(id);

        logger.info("board : "+board);
        int isOk;
        try {
            ProjectFileVO imgFile = null;
            if (files[0].getSize() > 0) { // 값이 있는지 체크
                imgFile = FH.uploadFiles(files); // 핸들러에 있는 메서드로 실제 파일들의 정보를 imgFile에 담아줌
                logger.info("imgFile :"+imgFile);
                isOk = BS.modifyBoard(new BoardDTO(board, imgFile));
            } else {
                isOk = BS.modifyOne(board); // 게시글만 존재 할 경우 게시글만 수정
            }

            if (isOk > 0) {
                logger.info("게시글 수정 성공");
                rattr.addFlashAttribute("msg", "BOARD_MOD_OK");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error("게시글 수정을 실패하였습니다. : {}", e.getMessage(), e);
            m.addAttribute(board);
            m.addAttribute("mode", "new");
            m.addAttribute("msg", "BOARD_WRT_ERR");
        }
        return "redirect:/board/list";
    }

    // 게시글을 추천하는 메서드
    @GetMapping("/like")
    public ResponseEntity<String> like(@RequestParam("like") Integer like) {
        try {
            int isOk = BS.boardlike(like);
            if (isOk > 0) {
                logger.info("추천성공!");
            } else {
                throw new Exception();
            }
            return ResponseEntity.ok("Recommendation recorded for like: " + like);
        } catch (Exception e) {
            logger.error("추천을 실패하였습니다. : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the request");
        }
    }
}
