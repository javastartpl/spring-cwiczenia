package pl.javastart.shortener.link;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LinkResourceTest {
    @Autowired
    WebTestClient webTestClient;

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
}
