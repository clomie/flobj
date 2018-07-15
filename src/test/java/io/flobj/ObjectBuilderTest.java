package io.flobj;

import static org.assertj.core.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.flobj.ObjectBuilder;

class ObjectBuilderTest {

    ObjectBuilder sut;

    @BeforeEach
    void beforeEach() throws Exception {
        sut = new ObjectBuilder();
    }

    @Test
    void test() throws Exception {
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:9781421536439");
        Object expected = new ObjectMapper().readValue(url, Object.class);

        // @formatter:off
        Object actual =
            sut
                .put(".kind", "books#volumes")
                .put(".totalItems", 1)
                .put(".items[0].kind", "books#volume")
                .put(".items[0].id", "JZbYQgAACAAJ")
                .put(".items[0].etag", "1GRKtB9MwLg")
                .put(".items[0].selfLink", "https://www.googleapis.com/books/v1/volumes/JZbYQgAACAAJ")
                .put(".items[0].volumeInfo.title", "Harmony")
                .put(".items[0].volumeInfo.authors[0]", "Project Itoh")
                .put(".items[0].volumeInfo.publisher", "Haikasoru")
                .put(".items[0].volumeInfo.publishedDate", "2010-07-20")
                .put(".items[0].volumeInfo.description", "In a perfect world, there is no escape Reads L to R (Western Style). In a perfect world, there is no escape In the future, Utopia has finally been achieved thanks to medical nanotechnology and a powerful ethic of social welfare and mutual consideration. This perfect world isn't that perfect though, and three young girls stand up to totalitarian kindness and super-medicine by attempting suicide via starvation. It doesn't work, but one of the girls--Tuan Kirie--grows up to be a member of the World Health Organization. As a crisis threatens the harmony of the new world, Tuan rediscovers another member of her suicide pact, and together they must help save the planet...from itself.")
                .put(".items[0].volumeInfo.industryIdentifiers[0].type", "ISBN_10")
                .put(".items[0].volumeInfo.industryIdentifiers[0].identifier", "1421536439")
                .put(".items[0].volumeInfo.industryIdentifiers[1].type", "ISBN_13")
                .put(".items[0].volumeInfo.industryIdentifiers[1].identifier", "9781421536439")
                .put(".items[0].volumeInfo.readingModes.text", false)
                .put(".items[0].volumeInfo.readingModes.image", false)
                .put(".items[0].volumeInfo.pageCount", 300)
                .put(".items[0].volumeInfo.printType", "BOOK")
                .put(".items[0].volumeInfo.categories[0]", "Fiction")
                .put(".items[0].volumeInfo.averageRating", 4.0)
                .put(".items[0].volumeInfo.ratingsCount", 1)
                .put(".items[0].volumeInfo.maturityRating", "NOT_MATURE")
                .put(".items[0].volumeInfo.allowAnonLogging", false)
                .put(".items[0].volumeInfo.contentVersion", "preview-1.0.0")
                .put(".items[0].volumeInfo.imageLinks.smallThumbnail", "http://books.google.com/books/content?id=JZbYQgAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api")
                .put(".items[0].volumeInfo.imageLinks.thumbnail", "http://books.google.com/books/content?id=JZbYQgAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
                .put(".items[0].volumeInfo.language", "en")
                .put(".items[0].volumeInfo.previewLink", "http://books.google.co.jp/books?id=JZbYQgAACAAJ&dq=isbn:9781421536439&hl=&cd=1&source=gbs_api")
                .put(".items[0].volumeInfo.infoLink", "http://books.google.co.jp/books?id=JZbYQgAACAAJ&dq=isbn:9781421536439&hl=&source=gbs_api")
                .put(".items[0].volumeInfo.canonicalVolumeLink", "https://books.google.com/books/about/Harmony.html?hl=&id=JZbYQgAACAAJ")
                .put(".items[0].saleInfo.country", "JP")
                .put(".items[0].saleInfo.saleability", "NOT_FOR_SALE")
                .put(".items[0].saleInfo.isEbook", false)
                .put(".items[0].accessInfo.country", "JP")
                .put(".items[0].accessInfo.viewability", "NO_PAGES")
                .put(".items[0].accessInfo.embeddable", false)
                .put(".items[0].accessInfo.publicDomain", false)
                .put(".items[0].accessInfo.textToSpeechPermission", "ALLOWED")
                .put(".items[0].accessInfo.epub.isAvailable", false)
                .put(".items[0].accessInfo.pdf.isAvailable", false)
                .put(".items[0].accessInfo.webReaderLink", "http://play.google.com/books/reader?id=JZbYQgAACAAJ&hl=&printsec=frontcover&source=gbs_api")
                .put(".items[0].accessInfo.accessViewStatus", "NONE")
                .put(".items[0].accessInfo.quoteSharingAllowed", false)
                .put(".items[0].searchInfo.textSnippet", "As a crisis threatens the harmony of the new world, Tuan rediscovers another member of her suicide pact, and together they must help save the planet...from itself.")
                .build();
        // @formatter:on

        assertThat(actual).isEqualTo(expected);
    }

}
