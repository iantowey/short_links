package com.neueda.url.shortner.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.url.shortner.model.Link;
import com.neueda.url.shortner.services.LinkShortenerService;
import com.neueda.url.shortner.services.StatisticsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class ShortenerController {

    @Resource
    ObjectMapper objectMapper;
    @Resource
    private LinkShortenerService linkShortenerService;
    @Resource
    private StatisticsService statisticsService;
    @Resource
    private HttpServletRequest request;
    private Function<HttpServletRequest, Map<String, String>> requestHeadersToJsonString = (request) ->
            Collections
                    .list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(o -> o, o -> request.getHeader(String.valueOf(o))));

    @RequestMapping(value = "/ly/{short_link}", method = RequestMethod.GET)
    public ModelAndView redirect(@PathVariable("short_link") String shortPath, ModelMap model) {
        String remoteAddr = request.getRemoteAddr();
        Link l = linkShortenerService.getLinkFromShortPath(shortPath);
        statisticsService.saveHitMetric(l, getCLientInfo(request));
        String url = l.getUrl();
        return new ModelAndView("redirect:http://" + url);
    }

    @RequestMapping(value = "/short/links", method = RequestMethod.GET)
    public List<Link> allLinks() {
        return linkShortenerService.getAll();
    }

    @RequestMapping(value = "/short/link", method = RequestMethod.POST)
    public Link generate_link(@RequestBody String body) {
        return linkShortenerService.getAndOrAddLink(body);
    }

    private String getCLientInfo(final HttpServletRequest request) {

        try {
            return objectMapper.writeValueAsString(requestHeadersToJsonString.apply(request));
        } catch (JsonProcessingException e) {
            return "{\"status\":\"ERROR : " + e.getMessage() + "\"}";
        }
    }
}
