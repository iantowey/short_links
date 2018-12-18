package com.neueda.url.shortner.repository;

import com.neueda.url.shortner.model.Link;
import com.neueda.url.shortner.repository.LinkRepository;
import com.neueda.url.shortner.services.LinkShortenerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Category(com.neueda.url.shortner.IntegrationTest.class)
public class LinkRepositoryIntegrationTests {

    private static final String url = "www.demo.com";

    @Resource
    LinkRepository linkRepository;

    @Test
    public void resourceWiring() throws SQLException {
        Assert.assertNotNull(linkRepository);
    }

    @Test
    public void crud() throws SQLException {
        String shortPath = LinkShortenerService.createUniqueShortPathForUrl(url);

        Link link = Link.builder()
                .id(null)
                .url(url)
                .shortPath(shortPath)
                .shortUrl("http://127.0.0.1/ly/"+shortPath)
                .creationDate(new java.sql.Timestamp(new Date().getTime()))
                .hitMetrics(null)
                .build();

        link = linkRepository.save(link);
        Assert.assertNotNull(link.getId());
        linkRepository.delete(link);
        link = linkRepository.findByShortPath(shortPath);
        Assert.assertNull(link);
    }



}
