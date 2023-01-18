package pl.javastart.shortener.redirect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.javastart.shortener.link.LinkService;
import pl.javastart.shortener.link.dto.LinkDto;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest
class RedirectControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    LinkService linkService;

    @Test
    void shouldReturnNotFound() {
        when(linkService.findLinkById("33d2513966")).thenReturn(Optional.empty());
        webTestClient
                .get()
                .uri("/redir/33d2513966")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldReturn302ForExistingLink() {
        LinkDto shortLink = new LinkDto(
                "33d2513966",
                "Kurs programowania",
                "https://javastart.pl",
                "http://localhost:8080/redir/33d2513966",
                0);
        when(linkService.incrementVisitsById("33d2513966")).thenReturn(Optional.of(shortLink));
        webTestClient
                .get()
                .uri("/redir/33d2513966")
                .exchange()
                .expectStatus()
                .is3xxRedirection();
    }
}
