package com.davejang.blockchain_ticketing.event.controller;

import com.davejang.blockchain_ticketing.common.service.OwnCloudService;
import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.dto.EventFormDto;
import com.davejang.blockchain_ticketing.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping(value = "/event")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final OwnCloudService ownCloudService;
    @Value("${owncloud.url}")
    private String ownCloudUrl;
    @Value("${owncloud.username}")
    private String ownCloudUsername;
    @Value("${owncloud.password}")
    private String ownCloudPassword;

    @Autowired
    public EventController(EventService eventService,
                           OwnCloudService ownCloudService) {
        this.eventService = eventService;
        this.ownCloudService = ownCloudService;
    }

    @GetMapping(value = "/{id}")
    public String eventInformation(Model model,
                                   @PathVariable Long id) {

        try {
            Event event = eventService.getEvent(id);
            model.addAttribute("event", event);
        } catch (Exception e) {
            return "/error/404";
        }

        return "eventInformation";
    }

    @PostMapping
    public String registerEvent(Model model,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute EventFormDto eventForm) {

        String filePath = "";
        String directory = "event";

        try {
            MultipartFile file = eventForm.getPoster();
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());

            filePath = ownCloudService.uploadToOwnCloud(tempFile, file.getOriginalFilename(), directory);
            Files.deleteIfExists(tempFile);
        }
        catch (AccessDeniedException e) {
            redirectAttributes.addFlashAttribute("message", "파일 업로드 실패");
            return "redirect:/admin/event-console/register";
        }
        catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/event-console/register";
        }

        try {
            Event registerEvent = eventService.registerEvent(
                    filePath,
                    eventForm.getEventName(),
                    eventForm.getDescription(),
                    eventForm.getLocation(),
                    eventForm.getPerformanceTime(),
                    eventForm.getRating(),
                    eventForm.getPrice(),
                    eventForm.getStartDate(),
                    eventForm.getEndDate()
            );
        }
        catch (IllegalArgumentException e) {
            String infoMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("message", infoMessage);
            return "redirect:/admin/event-console/register";
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "redirect:/admin/event-console";
    }

    @DeleteMapping
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
