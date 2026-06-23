package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardDto_forList;
import com.example.demo_webPet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
