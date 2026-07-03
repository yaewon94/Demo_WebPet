package com.example.demo_webPet.board;

import com.example.demo_webPet.board.MissingAnimal.MissingAnimalBoardRepository;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.util.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MissingAnimalBoardRepository missingAnimalBoardRepository;

    @Transactional(readOnly = true)
    public boolean existsBoard(BoardType type, Long boardId) {
        if(type.equals(BoardType.MISSING_ANIMAL)){
            return missingAnimalBoardRepository.existsById(boardId);
        }
        return false;
    }

    public String getBoardListUrl(BoardType type){
        if(type.equals(BoardType.MISSING_ANIMAL)){
            return UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST;
        }
        return null;
    }

    public String getBoardDetailUrl(BoardType type, Long boardId){
        if(type.equals(BoardType.MISSING_ANIMAL)){
            return UriBuilder.getUrl(
                    UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL,
                    Map.of("id", boardId.toString()));
        }
        return null;
    }
}
