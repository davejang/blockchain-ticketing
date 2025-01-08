package com.davejang.blockchain_ticketing.event.controller;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.dto.EventFormDto;
import com.davejang.blockchain_ticketing.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

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
                                @ModelAttribute @Validated EventFormDto eventForm) {
        try {
            Event registerEvent = eventService.registerEvent(
                    eventForm.getEventName(),
                    eventForm.getDescription(),
                    LocalDate.parse(eventForm.getStartDate()),
                    LocalDate.parse(eventForm.getEndDate())
            );
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", true);
//            return "redirect:/admin/event-console/#";
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "redirect:/admin/event-console";
    }
}
