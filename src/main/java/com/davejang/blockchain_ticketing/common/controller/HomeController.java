package com.davejang.blockchain_ticketing.common.controller;

import com.davejang.blockchain_ticketing.banner.domain.Banner;
import com.davejang.blockchain_ticketing.banner.service.BannerService;
import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/")
public class HomeController {

    private final EventService eventService;
    private final BannerService bannerService;

    @Autowired
    public HomeController(EventService eventService,
                          BannerService bannerService ) {
        this.eventService = eventService;
        this.bannerService = bannerService;
    }

    @GetMapping
    public String goToDashBoard(Model model) {
        List<Event> eventList = eventService.findAllEvents();
        model.addAttribute("eventList", eventList);

        List<Banner> bannerList = bannerService.findAllBanners();
        model.addAttribute("bannerList", bannerList);

        return "dashBoard";
    }
}
