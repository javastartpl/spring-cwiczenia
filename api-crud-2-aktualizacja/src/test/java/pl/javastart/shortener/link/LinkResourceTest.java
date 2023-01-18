package pl.javastart.shortener.link;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.javastart.shortener.link.dto.LinkCreateDto;
import pl.javastart.shortener.link.dto.LinkDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LinkResourceTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    LinkService linkService;

    @Test
    void shouldShortenLink() {
        String link = """
                {
                    "name": "Kurs programowania",
                    "targetUrl": "https://javastart.pl"
                }""";
        webTestClient.post()
                .uri("/api/links")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(link)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void shouldNotUpdateLinkIfNotExists() {
        String patch = """
                {
                    "name": "Kurs programowania",
                    "password": "asdf"
                }""";
        String notExistingId = "asdf1234";
        webTestClient.patch()
                .uri("/api/links/" + notExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patch)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldUpdateLinkWithCorrectPassword() {
        LinkCreateDto linkCreateDto = new LinkCreateDto();
        linkCreateDto.setName("Nazwa do zmiany");
        linkCreateDto.setTargetUrl("https://javastart.pl");
        linkCreateDto.setPassword("strongpassword");
        String patch = """
                {
                    "name": "Nowy Kurs programowania",
                    "password": "strongpassword"
                }""";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        LinkDto linkDto = linkService.shortenLink(linkCreateDto);
        webTestClient.patch()
                .uri("/api/links/" + linkDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patch)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void shouldNotUpdateLinkWithIncorrectPassword() {
        LinkCreateDto linkCreateDto = new LinkCreateDto();
        linkCreateDto.setName("Nazwa do zmiany");
        linkCreateDto.setTargetUrl("https://javastart.pl");
        linkCreateDto.setPassword("strongpassword");
        String patch = """
                {
                    "name": "Nowy Kurs programowania",
                    "password": "wrongpassword"
                }""";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        LinkDto linkDto = linkService.shortenLink(linkCreateDto);
        webTestClient.patch()
                .uri("/api/links/" + linkDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patch)
                .exchange()
                .expectStatus().isForbidden();
    }
}
