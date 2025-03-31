package com.example.urlshorterner.controller;

import com.example.urlshorterner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    // API to shorten URL
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String longUrl) {
        String shortUrl = urlService.generateShortUrl(longUrl);
        return ResponseEntity.ok(shortUrl);
    }

    // API to redirect to original URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrl(shortUrl);
        if (longUrl != null) {
            return ResponseEntity.ok(longUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
