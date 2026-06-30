package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.auth.AuthService;
import com.example.demo_webPet.auth.LoginUserDetail;
import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardDeniedException;
import com.example.demo_webPet.board.BoardQueryService;
import com.example.demo_webPet.board.BoardType;
import com.example.demo_webPet.common.exception.CustomNotValidException;
import com.example.demo_webPet.common.exception.ErrorCode;
import com.example.demo_webPet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private static final int MAX_SIZE_GUEST_USERNAME = 10;
    private static final int MAX_SIZE_GUEST_PASSWORD = 10;

    private final BoardCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BoardQueryService boardQueryService;

    @Transactional(readOnly = true)
    public Page<BoardCommentResponse> getCommentList(BoardType boardType, Long boardId, int page){
        Pageable pageable = PageRequest.of(page, BoardConstants.PAGE_SIZE);
        return commentRepository.findCommentList(boardType, boardId, pageable);
    }

    BoardCommentResponse addComment(BoardCommentWriteRequest request){
        LoginUserDetail loginUser = authService.getUser();
        if(loginUser == null){
            return addCommentGuest(request);
        }else{
            return addCommentLoginUser(request, loginUser.getDto().id());
        }
    }

    @Transactional
    private BoardCommentResponse addCommentLoginUser(BoardCommentWriteRequest request, Long userId){
        // DB 저장
        if(boardQueryService.existsBoard(request.boardType(), request.boardId())){
            BoardComment comment = new BoardComment();
            comment.setUser(userRepository.getReferenceById(userId));
            comment.setBoardType(request.boardType());
            comment.setBoardId(request.boardId());
            comment.setContent(request.content());
            commentRepository.save(comment);

            return new BoardCommentResponse(
                    comment.getId(),
                    comment.getUser().getUserName(),
                    comment.getContent(),
                    comment.getCreatedAt());
        }else{
            throw new BoardDeniedException(ErrorCode.BOARD_NOT_EXIST);
        }
    }

    @Transactional
    private BoardCommentResponse addCommentGuest(BoardCommentWriteRequest request){
        // validation 체크
        if(request.userName().isBlank()){
            throw new CustomNotValidException(Map.of("userName", "작성자 이름을 입력하세요"));
        }else if(request.userName().length() > MAX_SIZE_GUEST_USERNAME){
            String errorMsg = "작성자 이름은 " + MAX_SIZE_GUEST_USERNAME + "글자 이하만 가능합니다";
            throw new CustomNotValidException(Map.of("userName", errorMsg));
        }else if(request.password().isBlank()){
            throw new CustomNotValidException(Map.of("password", "비밀번호를 입력하세요"));
        }else if(request.password().length() > MAX_SIZE_GUEST_PASSWORD){
            String errorMsg = "비밀번호는 " + MAX_SIZE_GUEST_PASSWORD + "글자 이하만 가능합니다";
            throw new CustomNotValidException(Map.of("password", errorMsg));
        }

        // DB 저장
        if(boardQueryService.existsBoard(request.boardType(), request.boardId())){
            BoardComment comment = new BoardComment();
            comment.setGuestUserName(request.userName());
            comment.setGuestPassword(request.password());
            comment.setBoardType(request.boardType());
            comment.setBoardId(request.boardId());
            comment.setContent(request.content());
            commentRepository.save(comment);

            return new BoardCommentResponse(
                    comment.getId(),
                    comment.getGuestUserName(),
                    comment.getContent(),
                    comment.getCreatedAt());
        }else{
            throw new BoardDeniedException(ErrorCode.BOARD_NOT_EXIST);
        }
    }
}
