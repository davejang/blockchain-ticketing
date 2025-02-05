package com.davejang.blockchain_ticketing.admin;

import com.davejang.blockchain_ticketing.banner.domain.Banner;
import com.davejang.blockchain_ticketing.banner.service.BannerService;
import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.service.EventService;
import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final MemberService memberService;
    private final EventService eventService;
    private final BannerService bannerService;

    @Autowired
    public AdminController(MemberService memberService,
                           EventService eventService,
                           BannerService bannerService) {
        this.memberService = memberService;
        this.eventService = eventService;
        this.bannerService = bannerService;
    }

    @GetMapping("/dashboard")
    public String dashBoard(Model model) {
        return "adminPage";
    }

    @GetMapping("/event-console")
    public String eventManagement(Model model) {
        List<Event> eventList = eventService.findAllEvents();
        model.addAttribute("eventList", eventList);

        return "eventRegister";
    }

    @GetMapping("/event-console/register")
    public String eventRegisterForm(Model model) {
        return "eventRegisterForm";
    }

    @GetMapping("/user-console")
    public String userManagement(Model model) {
        List<Member> memberList = memberService.findAllMembers();
        model.addAttribute("memberList", memberList);

        return "userList";
    }

    @GetMapping("/banner-console")
    public String bannerManagement(Model model) {
        List<Banner> bannerList = bannerService.findAllBanners();
        model.addAttribute("bannerList", bannerList);

        return "bannerRegister";
    }

    @GetMapping("/banner-console/register")
    public String bannerRegisterForm(Model model) {
        return "bannerRegisterForm";
    }
}
