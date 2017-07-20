package info.tiefenauer.readinglist;

import info.tiefenauer.readinglist.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MockMvcWebTests {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .build();
    }

    @Test
    public void homePage() throws Exception {
        mockMvc.perform(get("/readingList/dani"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    public void postBook() throws Exception {
        // arrange
        final String reader = "dani";
        final String view = "readingList";
        final String url = String.format("/%s/%s", view, reader);
        final Long id = 1L;
        final String title = "BOOK TITLE";
        final String author = "BOOK AUTHOR";
        final String isbn = "1234567890";
        final String description = "DESCRIPTION";

        Book expectedBook = createBook(reader, id, title, author, isbn, description);

        // act #1
        mockMvc.perform(post(url)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .param("title", title)
                    .param("author", author)
                    .param("isbn", isbn)
                    .param("description", description))
        // assert #1
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", url));

        // act #2
        mockMvc.perform(get(url))
        // assert #2
                .andExpect(status().isOk())
                .andExpect(view().name(view))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));

    }

    private Book createBook(String reader, Long id, String title, String author, String isbn, String description) {
        Book book = new Book();
        book.setReader(reader);
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setDescription(description);
        return book;
    }

}
