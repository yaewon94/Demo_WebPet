package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.auth.AuthService;
import com.example.demo_webPet.auth.LoginUserDetail;
import com.example.demo_webPet.board.*;
import com.example.demo_webPet.common.constants.CommonValidConstants;
import com.example.demo_webPet.common.exception.AccessDeniedException;
import com.example.demo_webPet.common.exception.CustomNotValidException;
import com.example.demo_webPet.common.error.ErrorCode;
import com.example.demo_webPet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class BoardCommentService {

    private final BoardCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BoardService boardService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    Page<BoardCommentResponse> getCommentList(BoardType boardType, Long boardId, int page){
        Pageable pageable = PageRequest.of(
                page,
                BoardConstants.PAGE_SIZE,
                Sort.by("createdAt").ascending());
        Page<BoardComment> comments = commentRepository.findByBoardTypeAndBoardId(boardType, boardId, pageable);
        LoginUserDetail loginUser = authService.getUser();
        Long loginUserId = loginUser != null? loginUser.getDto().id() : null;

        return comments.map(comment -> {
            // 비회원 댓글
            if (comment.getUser() == null) {
                return new BoardCommentResponse(
                        comment.getId(),
                        comment.getGuestUserName(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        true,
                        true
                );
            }
            // 회원 댓글
            return new BoardCommentResponse(
                    comment.getId(),
                    comment.getUser().getUserName(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    Objects.equals(loginUserId, comment.getUser().getId()),
                    false
            );
        });
    }

    void addComment(BoardCommentWriteRequest request){
        // 게시물 존재 여부 확인
        checkBoardExist(request);

        // 로그인 여부에 따라 DB 저장 메소드 호출
        LoginUserDetail loginUser = authService.getUser();
        if(loginUser == null){
            addCommentGuest(request);
        }else{
            addCommentLoginUser(request, loginUser.getDto().id());
        }
    }

    @Transactional
    void modifyComment(BoardCommentWriteRequest request) {
        // 게시물, 댓글 존재 여부 확인, 권한 체크
        BoardComment comment = checkConditionForModifyDelete(request);
        // DB 변경 (영속성 변화 감지)
        comment.setGuestUserName(request.userName());
        comment.setContent(request.content());
    }

    @Transactional
    void deleteComment(BoardCommentDeleteRequest request) {
        // 게시물, 댓글 존재 여부 확인, 권한 체크
        BoardComment comment = checkConditionForModifyDelete(request);
        // DB에서 삭제
        commentRepository.deleteById(request.commentId());
        // TODO : 게시물 삭제시 댓글도 삭제
    }

    @Transactional
    private void addCommentLoginUser(BoardCommentWriteRequest request, Long userId){
        // DB 저장
        if(boardService.existsBoard(request.boardType(), request.boardId())){
            BoardComment comment = new BoardComment();
            comment.setUser(userRepository.getReferenceById(userId));
            comment.setBoardType(request.boardType());
            comment.setBoardId(request.boardId());
            comment.setContent(request.content());
            commentRepository.save(comment);
        }else{
            throw new AccessDeniedException(
                    ErrorCode.BOARD_NOT_EXIST,
                    boardService.getBoardListUrl(request.boardType()));
        }
    }

    @Transactional
    private void addCommentGuest(BoardCommentWriteRequest request){
        // validation 체크
        validationCheckGuestComment(request);

        // DB 저장
        if(boardService.existsBoard(request.boardType(), request.boardId())){
            BoardComment comment = new BoardComment();
            comment.setGuestUserName(request.userName());
            // TODO : 패스워드 암호화 관련 공통 클래스, 메소드 만들기
            comment.setGuestPassword(passwordEncoder.encode(request.password()));
            comment.setBoardType(request.boardType());
            comment.setBoardId(request.boardId());
            comment.setContent(request.content());
            commentRepository.save(comment);
        }else{
            throw new AccessDeniedException(
                    ErrorCode.BOARD_NOT_EXIST,
                    boardService.getBoardListUrl(request.boardType()));
        }
    }

    private void validationCheckGuestComment(BoardCommentWriteRequest request){
        if(request.userName().isBlank()){
            throw new CustomNotValidException(ErrorCode.BOARD_COMMENT_USERNAME_REQUIRED);
        }else if(request.userName().length() > CommonValidConstants.MAX_SIZE_BOARD_COMMENT_GUEST_USERNAME){
            throw new CustomNotValidException(ErrorCode.BOARD_COMMENT_USERNAME_NOT_VALID);
        }
        validationCheckGuestComment((BoardCommentRequest) request);
    }

    private void validationCheckGuestComment(BoardCommentRequest request){
        if(request.password().isBlank()){
            throw new CustomNotValidException(ErrorCode.BOARD_COMMENT_PASSWORD_REQUIRED);
        }else if(request.password().length() > CommonValidConstants.MAX_SIZE_BOARD_COMMENT_GUEST_PASSWORD){
            throw new CustomNotValidException(ErrorCode.BOARD_COMMENT_PASSWORD_NOT_VALID);
        }
    }

    private void checkBoardExist(BoardCommentRequest request){
        // 댓글이 존재하지 않는 경우 boardType, boardId를 DB로 알아낼수가 없어서
        // 댓글 자체가 존재하지 않는건지, 댓글이 포함된 게시물이 지워져서 없어진건지 알수가 없으므로
        // DTO에 boardType, boardId를 넣었음
        if(!boardService.existsBoard(request.boardType(), request.boardId())){
            throw new AccessDeniedException(
                    ErrorCode.BOARD_NOT_EXIST,
                    boardService.getBoardListUrl(request.boardType()));
        }
    }

    private BoardComment checkConditionForModifyDelete(BoardCommentRequest request) {
        // 게시물 존재 여부 체크
        checkBoardExist(request);
        // 댓글 존재 여부 체크
        BoardComment comment = commentRepository.findById(request.commentId())
                .orElseThrow(() ->
                        new AccessDeniedException(
                                ErrorCode.BOARD_COMMENT_NOT_EXIST,
                                boardService.getBoardDetailUrl(request.boardType(), request.boardId())));

        // 비회원 댓글일 경우 form validation, 비밀번호 일치하는지
        if(comment.getUser() == null){
            validationCheckGuestComment(request);
            if(!passwordEncoder.matches(
                    request.password(),
                    comment.getGuestPassword())){
                throw new CustomNotValidException(ErrorCode.BOARD_COMMENT_PASSWORD_MISMATCH);
            }
        }
        // 회원 댓글일 경우 본인이 쓴 글이 맞는지
        else{
            LoginUserDetail loginUserDetail = authService.getUser();
            if(loginUserDetail == null
                    || !loginUserDetail.getDto().id().equals(comment.getUser().getId())){
                throw new AccessDeniedException(ErrorCode.BOARD_COMMENT_ACCESS_DENIED);
            }
        }

        return comment;
    }
}
