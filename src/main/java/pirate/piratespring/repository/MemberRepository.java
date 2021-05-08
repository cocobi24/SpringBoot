package pirate.piratespring.repository;

import pirate.piratespring.domain.BusinessTimes;
import pirate.piratespring.domain.Holidays;
import pirate.piratespring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Member delete(Member member);
    BusinessTimes businessTimesCreate(BusinessTimes businessTimes);
    Holidays holidayCreate(Holidays holiday);
    Optional<Member> findByLevel(Long level);
    Optional<Member> findByName(String name);
    List<Member> findAll();


}
