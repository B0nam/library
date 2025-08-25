package com.bonam.library.domain.googlebooks.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.googlebooks.model.GoogleBookItemDTO;
import com.bonam.library.domain.googlebooks.model.GoogleBooksResponseDTO;
import com.bonam.library.domain.googlebooks.model.VolumeInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GetGoogleBooksService {

    @Value("${google.books.api.key:}")
    private String apiKey;

    private final WebClient webClient;

    public GetGoogleBooksService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.googleapis.com/books/v1").build();
    }

    public List<GoogleBookItemDTO> searchBooksByTitle(String title) {
        GoogleBooksResponseDTO response = webClient.get()
                .uri("/volumes?q=intitle:" + title + "&key=" + apiKey)
                .retrieve()
                .bodyToMono(GoogleBooksResponseDTO.class)
                .block();

        if (response == null || response.getItems() == null) {
            return List.of();
        }

        return response.getItems();
    }

    public Book getBookById(String googleBookId) {
        GoogleBookItemDTO item = webClient.get()
                .uri("/volumes/" + googleBookId + "?key=" + apiKey)
                .retrieve()
                .bodyToMono(GoogleBookItemDTO.class)
                .block();

        if (item == null || item.getVolumeInfo() == null) {
            throw new ResourceNotFoundException("Google Book", googleBookId);
        }

        VolumeInfoDTO info = item.getVolumeInfo();

        String isbn = (info.getIndustryIdentifiers() != null && !info.getIndustryIdentifiers().isEmpty())
                ? info.getIndustryIdentifiers().getFirst().getIdentifier()
                : "N/A";

        String author = (info.getAuthors() != null && !info.getAuthors().isEmpty())
                ? info.getAuthors().getFirst()
                : "Unknown";

        String category = "Uncategorized";
        if (info.getCategories() != null && !info.getCategories().isEmpty()) {
            String fullCategory = info.getCategories().getFirst();

            if (fullCategory.contains("/")) {
                category = fullCategory.split("/")[0].trim();
            } else {
                category = fullCategory.trim();
            }
        }

        return Book.builder()
                .title(info.getTitle())
                .author(author)
                .isbn(isbn)
                .publishDate(parsePublishedDate(info.getPublishedDate()))
                .category(category)
                .build();
    }

    private LocalDate parsePublishedDate(String rawDate) {
        if (rawDate == null) return LocalDate.now();
        try {
            if (rawDate.length() == 4) {
                return LocalDate.parse(rawDate + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else if (rawDate.length() == 7) {
                return LocalDate.parse(rawDate + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                return LocalDate.parse(rawDate, DateTimeFormatter.ISO_DATE);
            }
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}
