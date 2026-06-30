package com.example.demo_webPet.board;

import com.example.demo_webPet.board.MissingAnimal.MissingAnimalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

    private final MissingAnimalBoardRepository missingAnimalBoardRepository;

    public boolean existsBoard(BoardType type, Long boardId) {
        if(type.equals(BoardType.MISSING_ANIMAL)){
            return missingAnimalBoardRepository.existsById(boardId);
        }
        return false;
    }
}
