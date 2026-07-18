package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardDto_forList;
import com.example.demo_webPet.common.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo_webPet.common.error.ErrorCode.ERROR_BOARD_ACCESS_DENIED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class RescuedAnimalBoardService {

    private final RescuedAnimalSyncService syncService;
    private final RescuedAnimalBoardRepository boardRepository;

    Page<BoardDto_forList> getBoardList(int page){
        Pageable pageable = PageRequest.of(
                page,
                BoardConstants.PAGE_SIZE,
                BoardConstants.SORT
        );
        return boardRepository.findBoardList(pageable);
    }

    Page<BoardDto_forList> getBoardList(int page, String addressCode){
        Pageable pageable = PageRequest.of(
                page,
                BoardConstants.PAGE_SIZE,
                BoardConstants.SORT
        );
        return boardRepository.findBoardList(pageable, addressCode);
    }

    RescuedAnimalBoard getBoard(Long id){
        return boardRepository.findById(id).orElseThrow(() ->
                new AccessDeniedException(ERROR_BOARD_ACCESS_DENIED));
    }
}
