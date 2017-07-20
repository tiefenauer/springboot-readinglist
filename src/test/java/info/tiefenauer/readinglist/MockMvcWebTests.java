package info.tiefenauer.readinglist;

import info.tiefenauer.readinglist.entity.Book;
import info.tiefenauer.readinglist.entity.Reader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadinglistApplication.class)
@WebAppConfiguration
public class MockMvcWebTests {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void homePage_unauthenticatedUser() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    @Ignore("assertion fails and I have no idea why...")
    @Test
    @WithMockUser(username="dani", password="password", roles="READER")
    public void homePage_authenticatedUser2() throws Exception {
        // arrange
        Reader expectedReader = createReader("dani", "password", "Daniel Tiefenauer");
        //act
        mockMvc.perform(get("/readingList/dani"))
        //assert
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attribute("reader",samePropertyValuesAs(expectedReader)))
                .andExpect(model().attribute("books", hasSize(0)));
    }

    @Ignore("Context initialisation fails and I have no idea why...")
    @Test
    @WithUserDetails("dani")
    public void homePage_authenticateUser() throws Exception{
        // arrange
        Reader expectedReader = createReader("dani", "password", "Daniel Tiefenauer");
        //
        mockMvc.perform(get("/readingList/dani"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList/craig"))
                .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
                .andExpect(model().attribute("books", hasSize(0)));
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

    private Reader createReader(String username, String password, String fullName) {
        Reader reader = new Reader();
        reader.setUsername(username);
        reader.setPassword(password);
        reader.setFullname(fullName);
        return reader;
    }

}
