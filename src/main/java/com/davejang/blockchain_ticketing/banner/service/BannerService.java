package com.davejang.blockchain_ticketing.banner.service;

import com.davejang.blockchain_ticketing.banner.domain.Banner;
import com.davejang.blockchain_ticketing.banner.repository.BannerRepository;
import com.davejang.blockchain_ticketing.event.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BannerService {
    private final BannerRepository bannerRepository;

    @Autowired
    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> findAllBanners() {
        return bannerRepository.findAll();
    }

    @Transactional
    public void createBanner(String bannerTitle,
                             String imageUrl,
                             String linkUrl) {

        Banner banner = Banner.builder()
                .bannerTitle(bannerTitle)
                .imageUrl(imageUrl)
                .linkUrl(linkUrl)
                .build();

        bannerRepository.save(banner);
    }

    @Transactional
    public void deleteBanner(String bannerTitle) {
        Optional<Banner> banner = bannerRepository.findByBannerTitle(bannerTitle);

        if (banner.isEmpty()) {
            log.info("이벤트가 존재하지 않습니다. {}", bannerTitle);
            throw new IllegalArgumentException("존재하지 않는 이벤트입니다");
        }
        bannerRepository.delete(banner.get());
    }
}
