package pirate.piratespring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pirate.piratespring.domain.BusinessTimes;
import pirate.piratespring.domain.Holidays;
import pirate.piratespring.domain.Member;
import pirate.piratespring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** 점포등록 */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 점포 검증
        memberRepository.save(member);
        return member.getLevel();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 점포입니다.");
                });
    }

    /** 영업시간 등록 */
    public String businessTimesCreate(BusinessTimes businessTimes) {
        memberRepository.businessTimesCreate(businessTimes);
        return businessTimes.getName();
    }

    /** 점포 리스트 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /** 점포 삭제 */
    public Long delete(Member member) {
        memberRepository.delete(member);
        return member.getLevel();
    }

    /** 휴일 등록 */
    public Long holidayCreate(Holidays holidays) {
        memberRepository.holidayCreate(holidays);
        return holidays.getLevel();
    }

    public Optional<Member> findOne(Long memberLevel) {
        return memberRepository.findByLevel(memberLevel);
    }




}
