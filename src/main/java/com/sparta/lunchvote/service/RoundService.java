package com.sparta.lunchvote.service;

import com.sparta.lunchvote.dto.*;
import com.sparta.lunchvote.entity.LunchRound;
import com.sparta.lunchvote.entity.Menu;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
        if (roundRepository.existsByDate(date)) {
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
                    menu.setRound(round);
                    return menu;
                })
                .toList();
        round.setMenus(menuList);
        // 반환할 DTO 생성
        List<MenuResponse> menus = menuList.stream()
                .map(MenuResponse::from)
                .toList();
        //영속성
        LunchRound savedRound = roundRepository.save(round);

        return RoundResponse.from(savedRound);
    }

    @Transactional(readOnly = true)
    public RoundTodayResponse getToday(User user) {
        List<RoundResponse> roundResponses = new ArrayList<>();
        LocalDate date = LocalDate.now();

        LunchRound round = roundRepository.findByDate(date).orElseThrow(
                () -> new NullPointerException("오늘의 라운드가 아직 없습니다."));

        List<MenuResponse> menuList = round.getMenus().stream()
                .map(MenuResponse::from)
                .toList();

        int totalVotes = round.getMenus().stream()
                .mapToInt(Menu::getVoteCount)
                .sum();
        return new RoundTodayResponse(round.getId(),
                round.getUser().getName(),
                round.getDate(),
                menuList,
                totalVotes);
    }

    @Transactional(readOnly = true)
    public List<RoundListResponse> getAll(User user) {
        List<LunchRound> rounds = roundRepository.findAll();
        List<RoundListResponse> result = rounds.stream()
                .map(RoundListResponse::from)
                .toList();

        return result;
    }

    @Transactional
    public void delete(Long id, User user) {
        LunchRound round = roundRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 라운드입니다."));
        if(!round.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("사용자에게 해당 라운드를 삭제할 권한이 없습니다.");
        }

        roundRepository.delete(round);
    }
}
