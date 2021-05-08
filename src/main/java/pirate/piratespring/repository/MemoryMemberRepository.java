package pirate.piratespring.repository;

import org.springframework.stereotype.Repository;
import pirate.piratespring.domain.BusinessTimes;
import pirate.piratespring.domain.Holidays;
import pirate.piratespring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setLevel(++sequence);
        store.put(member.getLevel(), member);
        return member;
    }

    @Override
    public BusinessTimes businessTimesCreate(BusinessTimes businessTimes) {
        return businessTimes;
    }

    @Override
    public Member delete(Member member) {
        store.put(member.getLevel(), member);
        return member;
    }

    @Override
    public Holidays holidayCreate(Holidays holidays) {
        holidays.setLevel(++sequence);
        return holidays;
    }

    @Override
    public Optional<Member> findByLevel(Long level) {
        return Optional.ofNullable(store.get(level));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }
}