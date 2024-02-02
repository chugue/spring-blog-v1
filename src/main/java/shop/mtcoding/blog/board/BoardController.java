package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog._core.PagingUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final HttpSession session;
    private final BoardRepository boardRepository;

    // http://localhost:8080?page=0
    @GetMapping({"/", "/board"})
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {

        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList);

        int currentPage = page;
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);

        int boardTotalCount = boardRepository.totalPageCount();
        boolean first = PagingUtil.isFirst(currentPage);
        boolean last = PagingUtil.isLast(currentPage, boardTotalCount);
        request.setAttribute("last", last);
        request.setAttribute("first", first);

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {
        BoardResponse.DetailDTO responseDTO = boardRepository.findById(id);

        request.setAttribute("board", responseDTO);

        //1. session 정보에 접근해서 user의 id 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");

        boolean owner = false;
        //2. responseDTO 안에 있는 user의 id 가져오기
        int boardUserId = 0;
        boardUserId = responseDTO.getUserId();
        if (sessionUser != null){ // 로그인 했고
            if (boardUserId == sessionUser.getId()){
                owner= true;
            }
        }

        request.setAttribute("owner", owner);
        System.out.println(4);


        return "board/detail";
    }
}
