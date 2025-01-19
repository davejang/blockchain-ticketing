package com.davejang.blockchain_ticketing.event.controller;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.dto.EventFormDto;
import com.davejang.blockchain_ticketing.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import java.util.Base64;

@Controller
@RequestMapping(value = "/event")
@Slf4j
public class EventController {

    private final EventService eventService;
    @Value("${owncloud.url}")
    private String ownCloudUrl;
    @Value("${owncloud.username}")
    private String ownCloudUsername;
    @Value("${owncloud.password}")
    private String ownCloudPassword;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
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

        try {
            MultipartFile file = eventForm.getPoster();
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());

            filePath = uploadToOwnCloud(tempFile, file.getOriginalFilename());
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

    private String uploadToOwnCloud(Path filePath, String fileName) throws IOException {
        String uploadUrl = ownCloudUrl + "/remote.php/dav/files/admin/" + fileName;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(uploadUrl);

            String auth = ownCloudUsername + ":" + ownCloudPassword;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            httpPut.setHeader("Authorization", "Basic " + encodedAuth);

            FileEntity fileEntity = new FileEntity(filePath.toFile(), ContentType.DEFAULT_BINARY);
            httpPut.setEntity(fileEntity);

            HttpResponse response = httpClient.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();

            Header locationHeader = response.getFirstHeader("Location");
            if (locationHeader != null) {
                log.error("Redirecting to: {}", locationHeader.getValue());
            }
            String responseBody = EntityUtils.toString(response.getEntity());
            log.info("Response Code: {}", statusCode);
            log.info("Response Body: {}", responseBody);

            if (statusCode == 201 || statusCode == 200) {
                return uploadUrl;
            } else {
                throw new AccessDeniedException("파일 업로드 실패");
            }
        }
    }
}
