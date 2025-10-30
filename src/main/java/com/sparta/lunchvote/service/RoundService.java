package com.sparta.lunchvote.service;

import com.sparta.lunchvote.dto.MenuRequest;
import com.sparta.lunchvote.dto.MenuResponse;
import com.sparta.lunchvote.dto.RoundRequest;
import com.sparta.lunchvote.dto.RoundResponse;
import com.sparta.lunchvote.entity.LunchRound;
import com.sparta.lunchvote.entity.Menu;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {
    private final RoundRepository roundRepository;
    private final UserService userService;

    //Round 생성
    @Transactional
    public RoundResponse createRound(RoundRequest request, User user) {
        LocalDate date = request.getDate();
        //이미 라운드가 존재한다면
        if(roundRepository.existsByDate(date)) {
            throw new IllegalStateException("이미 오늘의 라운드가 존재합니다.");
        }
        //아직 오늘의 라운드가 없을 경우
        LunchRound round = new LunchRound(user, date);
        //메뉴 생성
        List<Menu> menuList = request.getMenus().stream()
                .map(menuRequest -> {
                    Menu menu = new Menu(menuRequest.getName(),
                            menuRequest.getType(),
                            menuRequest.getPrice(),
                            round);
                    return menu;})
                .toList();
        // 반환할 DTO 생성
        List<MenuResponse> menus = menuList.stream()
                .map(MenuResponse::from)
                .toList();
        //영속성
        LunchRound savedRound = roundRepository.save(round);

        return new RoundResponse(savedRound.getId(),
                savedRound.getUser().getId(),
                savedRound.getDate(),
                menus);
    }
}
