package shop.mtcoding.blog.board;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data //게터,세터, toString
@Entity
@Table(name = "board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private int userId;
    private LocalDateTime createdAt;


}
