package com.sparta.lunchvote.service;

import com.sparta.lunchvote.dto.GetVoteResponse;
import com.sparta.lunchvote.entity.LunchRound;
import com.sparta.lunchvote.entity.Menu;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.entity.Vote;
import com.sparta.lunchvote.repository.RoundRepository;
import com.sparta.lunchvote.repository.UserRepository;
import com.sparta.lunchvote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final RoundRepository roundRepository;
    private final UserRepository userRepository;

    @Transactional
    public void vote(Long menuId, Long userId) {
        LunchRound round = roundRepository.findByMenusId(menuId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 메뉴입니다."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 사용자입니다."));

        //메뉴 찾기
        Menu votedMenu = round.getMenus().stream().filter(menu -> menu.getId().equals(menuId)).findFirst().orElseThrow(
                () -> new IllegalStateException("해당 메뉴를 찾을 수 없습니다."));

        if(!voteRepository.existsByUserAndMenu(user, votedMenu)) {
            throw new IllegalArgumentException("(중복 투표 불가) 이미 해당 메뉴에 투표를 하셨습니다.");
        }
        Vote vote = new Vote(user, votedMenu);
        Vote savedVote = voteRepository.save(vote);

        votedMenu.setVoteCount(votedMenu.getVoteCount()+1);
        roundRepository.save(round); //영속성 - 변경된 voteCount 수 저장
    }

    @Transactional(readOnly = true)
    public List<GetVoteResponse> getMyVote(Long id) {
        List<Vote> voteList = new ArrayList<>();

        for (Vote vote : voteRepository.findAll()) {
            if(vote.getUser().getId().equals(id)) {
                voteList.add(vote);
            }
        }

        return voteList.stream().map(GetVoteResponse::from).toList();

    }

    @Transactional
    public void deleteVote(Long voteId, User user) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 투표 ID입니다."));

        if(!vote.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 투표한 기록만 삭제할 수 있습니다.");
        }
        voteRepository.delete(vote);
    }
}
