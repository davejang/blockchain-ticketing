package com.davejang.blockchain_ticketing.banner.controller;

import com.davejang.blockchain_ticketing.banner.domain.Banner;
import com.davejang.blockchain_ticketing.banner.dto.BannerFormDto;
import com.davejang.blockchain_ticketing.banner.service.BannerService;
import com.davejang.blockchain_ticketing.common.service.OwnCloudService;
import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.dto.EventFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping(value = "/banner")
@Slf4j
public class BannerController {
    private final BannerService bannerService;
    private final OwnCloudService ownCloudService;

    @Autowired
    public BannerController(BannerService bannerService,
                            OwnCloudService ownCloudService) {
        this.bannerService = bannerService;
        this.ownCloudService = ownCloudService;
    }

    @PostMapping
    public String registerBanner(Model model,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute BannerFormDto bannerForm) {

        String filePath = "";
        String directory = "banner";

        try {
            MultipartFile file = bannerForm.getBannerImage();
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());

            filePath = ownCloudService.uploadToOwnCloud(tempFile, file.getOriginalFilename(), directory);
            Files.deleteIfExists(tempFile);
        }
        catch (AccessDeniedException e) {
            redirectAttributes.addFlashAttribute("message", "파일 업로드 실패");
            return "redirect:/admin/banner-console/register";
        }
        catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/banner-console/register";
        }

        try {
            bannerService.createBanner(
                    bannerForm.getBannerTitle(),
                    filePath,
                    ""
            );
        }
        catch (IllegalArgumentException e) {
            String infoMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("message", infoMessage);
            return "redirect:/admin/banner-console/register";
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "redirect:/admin/banner-console";
    }

    @DeleteMapping
    public String deleteBanner(String bannerTitle) {

        try {
            bannerService.deleteBanner(bannerTitle);
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "redirect:/admin/banner-console";
    }
}
