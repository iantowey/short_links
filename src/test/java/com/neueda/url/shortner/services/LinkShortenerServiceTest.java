package com.neueda.url.shortner.services;

import com.neueda.url.shortner.model.Link;
import com.neueda.url.shortner.repository.LinkRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LinkShortenerServiceTest {

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Environment environment;


    @InjectMocks
    private LinkShortenerService linkShortenerService;

    @Test
    public void getAllTest(){
        when(linkRepository.findAll()).thenReturn(
                Stream.of(
                        Link.builder()
                                .id(1)
                                .url("bbc.co.uk")
                                .shortPath("dummy1")
                                .shortUrl("http://127.0.0.1/ly/dummy1")
                                .creationDate(new java.sql.Timestamp(new Date().getTime()))
                                .hitMetrics(null)
                                .build(),
                        Link.builder()
                                .id(2)
                                .url("rte.ie")
                                .shortPath("dummy2")
                                .shortUrl("http://127.0.0.1/ly/dummy1")
                                .creationDate(new java.sql.Timestamp(new Date().getTime()))
                                .hitMetrics(null)
                                .build()
                ).collect(Collectors.toList())
        );
        List<Link>  links = linkShortenerService.getAll();
        Assert.assertEquals(2, links.size());
    }

    @Test
    public void getLinkFromShortPathTest(){
        when(linkRepository.findByShortPath("dummy1")).thenReturn(
                        Link.builder()
                                .id(1)
                                .url("bbc.co.uk")
                                .shortPath("dummy1")
                                .shortUrl("http://127.0.0.1/ly/dummy1")
                                .creationDate(new java.sql.Timestamp(new Date().getTime()))
                                .hitMetrics(null)
                                .build());
        Link  link = linkShortenerService.getLinkFromShortPath("dummy1");
        Assert.assertEquals(1, link.getId().intValue());

    }

    @Test
    public void getAndOrAddLinkTest_exists(){
        when(linkRepository.findByShortPath("9ce6936e")).thenReturn(
                Link.builder()
                        .id(1)
                        .url("bbc.co.uk")
                        .shortPath("9ce6936e")
                        .shortUrl("http://127.0.0.1/ly/9ce6936e")
                        .creationDate(new java.sql.Timestamp(new Date().getTime()))
                        .hitMetrics(null)
                        .build());
        Link  link = linkShortenerService.getAndOrAddLink("www.bbc.co.uk");
        Assert.assertEquals(1, link.getId().intValue());
    }

    @Test
    public void getAndOrAddLinkTest_nonexists(){
        when(applicationContext.getBean(Environment.class)).thenReturn(environment);
        when(environment.getProperty("server.port", Integer.class)).thenReturn(Integer.parseInt("8080"));
        when(linkRepository.findByShortPath("9ce6936e")).thenReturn(null);
        when(linkRepository.save(any(Link.class))).thenReturn(Link.builder()
                .id(1)
                .url("bbc.co.uk")
                .shortPath("9ce6936e")
                .shortUrl("http://127.0.0.1/ly/9ce6936e")
                .creationDate(new java.sql.Timestamp(new Date().getTime()))
                .hitMetrics(null)
                .build());
        Link  link = linkShortenerService.getAndOrAddLink("www.bbc.co.uk");
        Assert.assertEquals(1, link.getId().intValue());
    }

}
