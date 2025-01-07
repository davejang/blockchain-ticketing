package com.davejang.blockchain_ticketing.event.controller;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.dto.EventFormDto;
import com.davejang.blockchain_ticketing.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/event")
@Slf4j
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/register")
    public String registerEvent(Model model,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute EventFormDto eventForm) {

        eventService.registerEvent(
                eventForm.getEventName(),
                eventForm.getDescription(),
                eventForm.getStartDate(),
                eventForm.getEndDate()
        );

        return "redirect:/admin/event-console";
    }
}
