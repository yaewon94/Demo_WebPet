package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.animal.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class RescuedAnimalSyncService {

    private final RescuedAnimalApiClient apiClient;
    private final RescuedAnimalBoardRepository boardRepository;
    private final AnimalRepository animalRepository;

    /*  Spring Boot 실행
            ↓
        @ComponentScan으로 Bean 찾음, 객체 생성
            ↓
        @Autowired / @RequiredArgsConstructor 의존성 주입 완료
            ↓
        @PostConstruct 실행
            ↓
        애플리케이션 준비 완료*/
    //@PostConstruct

    /*// Spring Boot 완전히 시작 후 호출됨
    // 접속자가 보호동물 게시판에 안들어온 경우 쓸일이 없기 때문에
    // 이때 초기화 하는건 비효율적
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        sync();
    }*/

    void syncIfNeeded(){
        if (boardRepository.count() > 0) {
            return;
        }

        synchronized (this) {
            // 다른 스레드가 먼저 채웠을 수도 있으니 한 번 더 확인
            if (boardRepository.count() > 0) {
                return;
            }
            sync();
        }
    }

    // @ cron = 초 분 시 일 월 요일
    //@Scheduled(cron = "0 0 */6 * * *") // 6시간마다 실행
    @Scheduled(fixedDelay = 60000) // 1분마다 실행 [개발모드]
    public void updateAnimals() {
        sync();
    }

    @Transactional
    private void sync() {
        // 외부 API 호출
        RescuedAnimalApiResponse response =apiClient.getAnimalList();

        // DTO 변환
        List<RescuedAnimalApiDto> animals = response
                .getResponse()
                .getBody()
                .getItems()
                .getItem();

        // Entity 변환 => DB 저장
        List<RescuedAnimalBoard> boards = new ArrayList<>();
        for(RescuedAnimalApiDto dto : animals){
            RescuedAnimalBoard board = boardRepository.getByDesertionNo(dto.desertionNo());
            if(board != null){
                board.update(dto);
            }else{
                Animal newAnimal = animalRepository.save(Animal.from(dto));
                RescuedAnimalBoard newBoard = RescuedAnimalBoard.from(dto);
                newBoard.setAnimal(newAnimal);
                boards.add(newBoard);
            }
        }
        boardRepository.saveAll(boards);
    }
}
