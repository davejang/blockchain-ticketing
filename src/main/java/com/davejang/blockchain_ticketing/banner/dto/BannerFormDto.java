package com.davejang.blockchain_ticketing.banner.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BannerFormDto {
    private MultipartFile bannerImage;

    private String bannerTitle;
}
