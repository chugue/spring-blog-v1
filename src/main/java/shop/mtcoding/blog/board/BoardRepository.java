package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import shop.mtcoding.blog._core.Constant;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public int totalPageCount() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Long totalCount = (Long)query.getSingleResult();
        return totalCount.intValue();
    }

    public List<Board> findAll(int page) {
         // 파이널로 하지 않아야 실수하지 않아. 한 페이지에 몇 개가 나오냐.
        int value = page * Constant.PAGING_COUNT; // 페이지네이션
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?, ?", Board.class); // 페이지네이션 만들 때는 쿼리 스트링을 쓴다. ?들어가는 것.
        query.setParameter(1, value);
        query.setParameter(2, Constant.PAGING_COUNT);
        List<Board> boardList = query.getResultList(); // 한 건이어도 리절트리스트로 받아라. 그리고 한 건일 때는 다운캐스팅
        return boardList;
    }


    public BoardResponse.DetailDTO findById(int id) {
        // Entity가 아닌 것은 JPA가  파싱 안해준다.
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?"); // 페이지네이션 만들 때는 쿼리 스트링을 쓴다. ?들어가는 것.
        query.setParameter(1, id);
        JpaResultMapper rm = new JpaResultMapper();
        BoardResponse.DetailDTO responseDTO = rm.uniqueResult(query, BoardResponse.DetailDTO.class);
        return responseDTO;
    }
}
