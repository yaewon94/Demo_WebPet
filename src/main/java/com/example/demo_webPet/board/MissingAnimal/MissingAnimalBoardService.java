package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardDeniedException;
import com.example.demo_webPet.board.BoardDto_forList;
import com.example.demo_webPet.common.output.HtmlUtils;
import com.example.demo_webPet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.example.demo_webPet.common.exception.ErrorCode.ERROR_BOARD_ACCESS_DENIED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class MissingAnimalBoardService {

    private final MissingAnimalBoardRepository boardRepository;
    private final UserRepository userRepository;

    // @ return : board id(pk)
    @Transactional
    Long addBoard(MissingAnimalBoardWriteRequest request, Long user_id){

        MissingAnimalBoard board = new MissingAnimalBoard();
        board.setUser(userRepository.getReferenceById(user_id));
        board.setTitle(request.title());
        board.setAnimal(Animal.from(request));
        board.setContent(HtmlUtils.sanitize(request.content()));

        boardRepository.save(board);
        return board.getId();
    }

    // @ return : board id(pk)
    @Transactional
    void modifyBoard(MissingAnimalBoardWriteRequest request, Long loginUserId){

        MissingAnimalBoard board = boardRepository.findById(request.id()).orElseThrow();

        // 다른 사람이 작성한 게시물에 수정하려는 경우
        if(!Objects.equals(board.getUser().getId(), loginUserId)){
            throw new BoardDeniedException(ERROR_BOARD_ACCESS_DENIED);
        }

        // entity 변경하면 JPA가 영속성 객체 변경 감지해서 DB 자동 update
        Animal animal = board.getAnimal();
        animal.update(
                request.species(),
                request.missingDate(),
                request.missingLocation()
        );
        board.update(
                request.title(),
                request.content());
    }

    @Transactional
    void deleteBoard(Long boardId, Long loginUserId){

        int deleted = boardRepository.deleteByIdAndUserId(boardId, loginUserId);
        if (deleted == 0) {
            // 삭제된 게시물이 없다 => 게시물 작성자 id != 현재 로그인 유저 id
            throw new BoardDeniedException(ERROR_BOARD_ACCESS_DENIED);
        }
    }


    MissingAnimalBoardWriteRequest getBoard(Long boardId, Long loginUserId){
        // 수정할 게시물이 없다 => 게시물 작성자 id != 현재 로그인 유저 id
        return boardRepository.findModifyDtoById(boardId, loginUserId)
                .orElseThrow(() ->
                        new BoardDeniedException(ERROR_BOARD_ACCESS_DENIED));
    }

    MissingAnimalBoardDetailResponse getBoard(Long id){
        return boardRepository.findDetailById(id).orElseThrow();
    }

    Page<BoardDto_forList> getBoardList(int page){

        Pageable pageable = PageRequest.of(
                page,
                BoardConstants.PAGE_SIZE,
                BoardConstants.SORT
        );

        return boardRepository.findBoardList(pageable);
    }
}
