package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.user.UserRepository;
import lombok.RequiredArgsConstructor;
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
}
