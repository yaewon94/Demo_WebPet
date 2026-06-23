package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardDeniedException;
import com.example.demo_webPet.board.BoardDto_forList;
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
        board.setImgUrl(request.imageUrl());
        board.setContent(request.content());

        boardRepository.save(board);
        return board.getId();
    }

    // @ return : board id(pk)
    @Transactional
    void modifyBoard(MissingAnimalBoardWriteRequest request, Long loginUserId){

        MissingAnimalBoard board = boardRepository.findById(request.id()).orElseThrow();

        // 다른 사람이 작성한 게시물에 수정,삭제 접근 하려는 경우
        if(!Objects.equals(board.getUser().getId(), loginUserId)){
            throw new BoardDeniedException(ERROR_BOARD_ACCESS_DENIED);
        }

        // entity 변경하면 JPA가 영속성 객체 변경 감지해서 DB 자동 update
        board.update(
                request.title(),
                request.content(),
                Animal.from(request),
                request.imageUrl());
    }

    MissingAnimalBoard getBoard(Long boardId, Long loginUserId){

        // 다른 사람이 작성한 게시물에 수정,삭제 접근 하려는 경우
        if(!Objects.equals(
                boardRepository.findById(boardId).orElseThrow().getUser().getId(),
                loginUserId)){
            throw new BoardDeniedException(ERROR_BOARD_ACCESS_DENIED);
        }
        return getBoard(boardId);
    }

    MissingAnimalBoard getBoard(Long id){
        return boardRepository.findById(id).orElseThrow();
    }

    Page<BoardDto_forList> getBoardList(int page){

        Pageable pageable = PageRequest.of(
                page,
                BoardConstants.PAGE_SIZE,
                BoardConstants.SORT
        );

        return boardRepository.findAll(pageable)
                        .map(board -> new BoardDto_forList(
                                board.getId(),
                                board.getTitle(),
                                board.getCreatedAt()
                        ));
    }
}
