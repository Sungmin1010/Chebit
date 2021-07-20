package com.nesty.chebit.service;

import com.nesty.chebit.domain.Member;
import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 신규 회원 가입
     * - 중복 가입 여부 확인 (email)
     */

    @Transactional
    public Long join(MemberJoinRequestDto memberJoinRequestDto){

        valiDateDuplicateMember(memberJoinRequestDto);
        return memberRepository.save(memberJoinRequestDto.toEntity());

    }

    private void valiDateDuplicateMember(MemberJoinRequestDto memberJoinRequestDto) {
        List<Member> findMemebrs = memberRepository.findByEmail(memberJoinRequestDto.getEmail());
        if(!findMemebrs.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public Long findId(String email){
        List<Member> findMember = memberRepository.findByEmail(email);
        return findMember.get(0).getId();
    }

    public List<MemberLoginDto> findMember(MemberLoginDto memberLoginDto){
        return memberRepository.findByEmail(memberLoginDto.getEmail()).stream()
                .map( o -> new MemberLoginDto(o))
                .collect(Collectors.toList());

    }
}
