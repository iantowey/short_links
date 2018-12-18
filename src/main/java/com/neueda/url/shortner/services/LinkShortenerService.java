package com.neueda.url.shortner.services;

import com.neueda.url.shortner.model.Link;
import com.neueda.url.shortner.repository.LinkRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class LinkShortenerService {

    @Resource
    LinkRepository linkRepository;

    @Resource
    ApplicationContext applicationContext;

    public static String createUniqueShortPathForUrl(String url) {
        return Integer.toHexString(DigestUtils.md5Hex(url).hashCode());
    }

    @Cacheable(value = "linkCache", key = "#key")
    public Link getLinkFromShortPath(final String key) {
        return linkRepository.findByShortPath(key);
    }

    public Link getAndOrAddLink(String url) {
        String shortPath = createUniqueShortPathForUrl(url);
        Link link = getLinkFromShortPath(shortPath);
        String ip;
        if (link == null) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = "127.0.0.1";
            }
            int port = applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class);

            link = Link.builder()
                    .id(null)
                    .url(url)
                    .shortPath(shortPath)
                    .shortUrl("http://" + ip + ":" + port + "/ly/" + shortPath)
                    .creationDate(new java.sql.Timestamp(new Date().getTime()))
                    .hitMetrics(null)
                    .build();
            link = linkRepository.save(link);
        }
        return link;
    }

    public List<Link> getAll() {
        return StreamSupport.stream(linkRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

}
