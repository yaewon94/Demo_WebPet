package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardDto_forList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class RescuedAnimalBoardService {

    private final RescuedAnimalSyncService syncService;
    private final RescuedAnimalBoardRepository boardRepository;

    Page<BoardDto_forList> getBoardList(int page){
        // DB 먼저 출력
        Pageable pageable = PageRequest.of(
                page,
                BoardConstants.PAGE_SIZE,
                BoardConstants.SORT
        );
        Page<BoardDto_forList> boards = boardRepository.findBoardList(pageable);

        // 동기화
        syncService.syncIfNeeded();

        return boards;
    }

    /*@Transactional
    void create(RescuedAnimalBoardWriteRequest request, Long userId){
        RescuedAnimalBoard board = new RescuedAnimalBoard();
        board.setUser(userRepository.getReferenceById(userId));
        board.setTitle(request.title());
        board.setContent(request.content());
        board.setShelter(); // TODO : user정보에서 shelter 정보 가져오기
        board.setAnimal(); // TODO : animalService를 통해 animal 엔티티 생성한 것으로 set, animal에 소속 shelter 저장
        repository.save(board);
    }*/
}
