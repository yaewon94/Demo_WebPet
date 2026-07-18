package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.shelter.Shelter;
import com.example.demo_webPet.shelter.ShelterService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class RescuedAnimalSyncService {

    private final RescuedAnimalApiClient apiClient;
    private final RescuedAnimalBoardRepository boardRepository;
    private final ShelterService shelterService;

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

    // Spring Boot 완전히 시작 후 호출됨
    // 접속자가 보호동물 게시판에 안들어온 경우 쓸일이 없기 때문에
    // 이때 초기화 하는건 비효율적
    // [debug ver]
    /*@EventListener(ApplicationReadyEvent.class)
    public void init() {
        syncAllData();
    }*/

    /*void syncIfNeeded(){
        if (boardRepository.count() > 0) {
            return;
        }

        synchronized (this) {
            // 다른 스레드가 먼저 채웠을 수도 있으니 한 번 더 확인
            if (boardRepository.count() > 0) {
                return;
            }
            syncAllData();
        }
    }*/


    // @ cron = 초 분 시 일 월 요일
    @Scheduled(cron = "0 0 */6 * * *") // 6시간마다 실행
    @Transactional
    public void syncAllData() {
        sync();
    }

    // 데이터 규모 : 몇백 ~ 몇천 건
    private void sync() {
        List<RescuedAnimalApiDto> apiData = apiClient.getAnimalList();
        Lists.partition(apiData, 100).forEach(this::syncBatch);
    }

    private void syncBatch(List<RescuedAnimalApiDto> batch) {
        // 외부 데이터 식별자 가져오기
        List<String> keys =
                batch.stream()
                        .map(RescuedAnimalApiDto::desertionNo)
                        .toList();

        // 식별자를 바탕으로 DB 데이터 모두 가져오기
        List<RescuedAnimalBoard> boards =
                boardRepository.findAllByDesertionNoIn(keys);

        // DB에서 가져온 데이터 map에 저장
        Map<String, RescuedAnimalBoard> map =
                boards.stream()
                        .collect(Collectors.toMap(
                                RescuedAnimalBoard::getDesertionNo,
                                b -> b
                        ));

        // map에 저장된 데이터와 비교해서 DB insert or update
        createOrUpdateData(batch, map);
    }

    private void createOrUpdateData(List<RescuedAnimalApiDto> src, Map<String, RescuedAnimalBoard> existingBoard){
        List<RescuedAnimalBoard> batch = new ArrayList<>();

        for (RescuedAnimalApiDto dto : src) {
            RescuedAnimalBoard board = existingBoard.get(dto.desertionNo());

            if(board != null){
                shelterService.update(dto);
                board.updateBoard(dto);
            }else{
                Shelter shelter = shelterService.createOrUpdate(dto);
                RescuedAnimalBoard newBoard = RescuedAnimalBoard.from(dto);
                newBoard.setShelter(shelter);

                batch.add(newBoard);
            }
        }

        if(!batch.isEmpty()){
            boardRepository.saveAll(batch);
        }
    }
}
