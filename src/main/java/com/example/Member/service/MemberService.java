package com.example.Member.service;

import com.example.Member.DTO.MemberDTO;
import com.example.Member.entity.MemberEntity;
import com.example.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
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

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }

        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);

        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);

        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있으므로 사용할 수 없음
            return null;
        } else {
            // 조회 결과가 없으므로 사용할 수 있음
            return "ok";
        }
    }
}
