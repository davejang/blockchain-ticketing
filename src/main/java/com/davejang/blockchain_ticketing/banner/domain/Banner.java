package com.davejang.blockchain_ticketing.banner.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bannerTitle;

    private String imageUrl;

    private String linkUrl;

    @Builder
    public Banner(String bannerTitle,
                  String imageUrl,
                  String linkUrl) {
        this.bannerTitle = bannerTitle;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }
}
