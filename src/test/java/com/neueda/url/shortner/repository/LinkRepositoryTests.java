package com.neueda.url.shortner.repository;

import com.neueda.url.shortner.model.Link;
import com.neueda.url.shortner.services.LinkShortenerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LinkRepositoryTests {

    private static final String url = "www.demo.com";

    @Mock
    private LinkRepository linkRepository;


    @Test
    public void saveTest() throws SQLException {
        String shortPath = LinkShortenerService.createUniqueShortPathForUrl(url);

        Link link = Link.builder()
                .id(null)
                .url(url)
                .shortPath(shortPath)
                .shortUrl("http://127.0.0.1/ly/"+shortPath)
                .creationDate(new java.sql.Timestamp(new Date().getTime()))
                .hitMetrics(null)
                .build();



        when(linkRepository.save(link))
                .thenReturn(
                        Link.builder()
                            .id(1)
                            .url(url)
                            .shortPath(shortPath)
                            .shortUrl("http://127.0.0.1/ly/"+shortPath)
                            .creationDate(new java.sql.Timestamp(new Date().getTime()))
                            .hitMetrics(null)
                        .build());
        link = linkRepository.save(link);
        Assert.assertNotNull(link.getId());
        Assert.assertEquals(1, link.getId().intValue());
    }



}
