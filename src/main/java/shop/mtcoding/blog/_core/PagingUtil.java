package shop.mtcoding.blog._core;

import shop.mtcoding.blog.board.BoardRepository;

public class PagingUtil {

    //첫 번째 페이지 여부
    public static boolean isFirst(int currentPage) {
        return currentPage == 0 ? true : false;
    }

    //마지막 페이지 여부
    public static boolean isLast(int currentPage, int boardTotalCount) {
        return boardTotalCount <= Constant.PAGING_COUNT * (currentPage + 1);
    }
}
