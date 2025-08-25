package com.bonam.library.domain.googlebooks.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.googlebooks.model.GoogleBookItemDTO;
import com.bonam.library.domain.googlebooks.model.GoogleBooksResponseDTO;
import com.bonam.library.domain.googlebooks.model.IndustryIdentifierDTO;
import com.bonam.library.domain.googlebooks.model.VolumeInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGoogleBooksServiceTest {

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.Builder webClientBuilder;

    private GetGoogleBooksService service;

    private static final String GOOGLE_BOOK_ID = "abc123";
    private static final String API_KEY = "test-api-key";

    @BeforeEach
    void setUp() {
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClientMock);

        when(webClientMock.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        service = new GetGoogleBooksService(webClientBuilder);
        ReflectionTestUtils.setField(service, "apiKey", API_KEY);
    }

    @Test
    void shouldReturnBookWhenFoundById() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals("Test Author", result.getAuthor());
        assertEquals("9781234567890", result.getIsbn());
        assertEquals(LocalDate.parse("2023-01-01"), result.getPublishDate());
        assertEquals("Fiction", result.getCategory());

        verify(requestHeadersUriSpec).uri("/volumes/" + GOOGLE_BOOK_ID + "?key=" + API_KEY);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFoundById() {
        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getBookById(GOOGLE_BOOK_ID)
        );

        assertEquals("Google Book not found with identifier: abc123", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenVolumeInfoIsNull() {
        GoogleBookItemDTO bookItem = new GoogleBookItemDTO();
        bookItem.setId(GOOGLE_BOOK_ID);

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getBookById(GOOGLE_BOOK_ID)
        );

        assertEquals("Google Book not found with identifier: abc123", exception.getMessage());
    }

    @Test
    void shouldHandleBookWithMissingIndustryIdentifiers() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setIndustryIdentifiers(null);

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals("N/A", result.getIsbn());
    }

    @Test
    void shouldHandleBookWithMissingAuthors() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setAuthors(null);

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals("Unknown", result.getAuthor());
    }

    @Test
    void shouldHandleBookWithMissingCategories() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setCategories(null);

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals("Uncategorized", result.getCategory());
    }

    @Test
    void shouldHandleBookWithCategoryContainingSlash() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setCategories(Collections.singletonList("Fiction/Fantasy"));

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals("Fiction", result.getCategory());
    }

    @Test
    void shouldParseYearOnlyPublishDate() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setPublishedDate("2023");

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals(LocalDate.parse("2023-01-01"), result.getPublishDate());
    }

    @Test
    void shouldParseYearMonthOnlyPublishDate() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setPublishedDate("2023-06");

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals(LocalDate.parse("2023-06-01"), result.getPublishDate());
    }

    @Test
    void shouldHandleInvalidPublishDate() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setPublishedDate("invalid-date");

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals(LocalDate.now().getYear(), result.getPublishDate().getYear());
        assertEquals(LocalDate.now().getMonth(), result.getPublishDate().getMonth());
        assertEquals(LocalDate.now().getDayOfMonth(), result.getPublishDate().getDayOfMonth());
    }

    @Test
    void shouldHandleNullPublishDate() {
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        bookItem.getVolumeInfo().setPublishedDate(null);

        when(responseSpec.bodyToMono(GoogleBookItemDTO.class)).thenReturn(Mono.just(bookItem));

        Book result = service.getBookById(GOOGLE_BOOK_ID);

        assertNotNull(result);
        assertEquals(LocalDate.now().getYear(), result.getPublishDate().getYear());
        assertEquals(LocalDate.now().getMonth(), result.getPublishDate().getMonth());
        assertEquals(LocalDate.now().getDayOfMonth(), result.getPublishDate().getDayOfMonth());
    }

    @Test
    void shouldSearchBooksByTitleAndReturnItems() {
        GoogleBooksResponseDTO responseDTO = new GoogleBooksResponseDTO();
        GoogleBookItemDTO bookItem = createGoogleBookItem();
        responseDTO.setItems(Collections.singletonList(bookItem));

        when(responseSpec.bodyToMono(GoogleBooksResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        List<GoogleBookItemDTO> results = service.searchBooksByTitle("Test Book");

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(GOOGLE_BOOK_ID, results.getFirst().getId());
        verify(requestHeadersUriSpec).uri("/volumes?q=intitle:Test Book&key=" + API_KEY);
    }

    @Test
    void shouldReturnEmptyListWhenSearchReturnsNoItems() {
        GoogleBooksResponseDTO responseDTO = new GoogleBooksResponseDTO();
        responseDTO.setItems(null);

        when(responseSpec.bodyToMono(GoogleBooksResponseDTO.class)).thenReturn(Mono.just(responseDTO));

        List<GoogleBookItemDTO> results = service.searchBooksByTitle("Non Existent Book");

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenSearchResponseIsNull() {
        when(responseSpec.bodyToMono(GoogleBooksResponseDTO.class)).thenReturn(Mono.empty());

        List<GoogleBookItemDTO> results = service.searchBooksByTitle("Any Title");

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    private GoogleBookItemDTO createGoogleBookItem() {
        GoogleBookItemDTO bookItem = new GoogleBookItemDTO();
        bookItem.setId(GOOGLE_BOOK_ID);

        VolumeInfoDTO volumeInfo = new VolumeInfoDTO();
        volumeInfo.setTitle("Test Book");
        volumeInfo.setAuthors(Collections.singletonList("Test Author"));
        volumeInfo.setPublishedDate("2023-01-01");

        IndustryIdentifierDTO identifier = new IndustryIdentifierDTO();
        identifier.setType("ISBN_13");
        identifier.setIdentifier("9781234567890");
        volumeInfo.setIndustryIdentifiers(Collections.singletonList(identifier));

        volumeInfo.setCategories(Collections.singletonList("Fiction"));

        bookItem.setVolumeInfo(volumeInfo);

        return bookItem;
    }
}
