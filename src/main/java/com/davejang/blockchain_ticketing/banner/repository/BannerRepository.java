package com.davejang.blockchain_ticketing.banner.repository;

import com.davejang.blockchain_ticketing.banner.domain.Banner;
import com.davejang.blockchain_ticketing.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findByBannerTitle(String bannerTitle);
}
