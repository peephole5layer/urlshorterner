package com.example.urlshorterner.service;

import com.example.urlshorterner.model.Url;
import com.example.urlshorterner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    // generate short url
    public String generateShortUrl(String longUrl) {
        Optional<Url> existingUrl = urlRepository.findByLongUrl(longUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortUrl();
        }

        // generte a Base64-encoded short url
        String shortUrl = Base64.getEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8))
                .substring(0, 8);

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        return shortUrl;
    }

    // get long url for short url
    public String getLongUrl(String shortUrl) {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);
        return urlOptional.map(Url::getLongUrl).orElse(null);
    }
}
