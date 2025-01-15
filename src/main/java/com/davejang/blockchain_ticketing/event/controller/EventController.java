package com.davejang.blockchain_ticketing.event.controller;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.dto.EventFormDto;
import com.davejang.blockchain_ticketing.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
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
                                @ModelAttribute EventFormDto eventForm) {

        try {
            Event registerEvent = eventService.registerEvent(
                    eventForm.getEventName(),
                    eventForm.getDescription(),
                    eventForm.getRating(),
                    eventForm.getStartDate(),
                    eventForm.getEndDate()
            );
        }
        catch (IllegalArgumentException e) {
            String infoMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("message", infoMessage);
            return "redirect:/admin/event/register";
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "redirect:/admin/event-console";
    }

    @DeleteMapping(value = "/delete")
    public String deleteEvent(String eventName) {

        try {
            eventService.deleteEvent(eventName);
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "redirect:/admin/event-console";
    }
}
