package com.example.Member.service;

import com.example.Member.DTO.MemberDTO;
import com.example.Member.entity.MemberEntity;
import com.example.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity((memberDTO));
        memberRepository.save(memberEntity);
        //  repository 의 save 메소드 호출 (entity 객체를 넘겨줘야 함)
    }

    public  MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if (byMemberEmail.isPresent()) {
            // 조회 결과 확인(해당 이메일을 가진 회원 정보)
            MemberEntity memberEntity = byMemberEmail.get();

            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인 실패)
                return null;
            }

        } else {
            //조회 결과 없음(해당 이메일을 가진 회원 정보 조회 불가능)
            return null;
        }
    }
}
