package pirate.piratespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pirate.piratespring.domain.Holidays;
import pirate.piratespring.domain.Member;
import pirate.piratespring.service.MemberService;

import java.util.List;

@Controller
public class MemberController<Holiday> {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 점포 등록
    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setOwner(form.getOwner());
        member.setDescription(form.getDescription());
        member.setAddress(form.getAddress());
        member.setPhone(form.getPhone());
        memberService.join(member);
        return "redirect:/";
    }

    //점포 삭제
    @GetMapping(value = "/members/delete")
    public String deleteForm() {
        return "members/deleteMemberForm";
    }

    @PostMapping(value = "/members/delete")
    public String delete(MemberForm form) {
        Member member = new Member();
        member.setLevel(form.getLevel());
        memberService.delete(member);
        return "redirect:/";
    }



    //점포 휴일 등록
    @GetMapping(value = "/members/holiday")
    public String holidayForm() {
        return "members/createHolidayForm";
    }

    @PostMapping(value = "/members/holiday")
    public String Holiday(HolidayForm holidayform) {
        Holidays holidays = new Holidays();
        holidays.setLevel(holidayform.getLevel());
        holidays.setHoliday(holidayform.getHoliday());
        memberService.holidayCreate(holidays);
        return "redirect:/";
    }



    //점포 리스트
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }






}