package com.neueda.url.shortner.services;

import com.neueda.url.shortner.model.Link;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Sql(scripts = "/sql/LinkShortenerServiceTest.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Category(com.neueda.url.shortner.IntegrationTest.class)
public class LinkShortenerServiceIntegrationTest {

    private static final String url = "www.demo.com";
    private static final String short_path = "739ea10e";

    @Resource
    LinkShortenerService linkShortenerService;

    @Test
    public void resourceWiring() throws SQLException {
        Assert.assertNotNull(linkShortenerService);
    }

    @Test
    public void getLink() throws SQLException {
        Link l = linkShortenerService.getAndOrAddLink(url);
        Assert.assertNotNull(l);
        Assert.assertNotNull(l.getShortPath().equals(short_path));
        l = linkShortenerService.getLinkFromShortPath(l.getShortPath());
        l = linkShortenerService.getLinkFromShortPath(l.getShortPath());
        l = linkShortenerService.getLinkFromShortPath(l.getShortPath());
        l = linkShortenerService.getLinkFromShortPath(l.getShortPath());
    }


}
