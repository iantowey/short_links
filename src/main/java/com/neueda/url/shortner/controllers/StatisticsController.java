package com.neueda.url.shortner.controllers;

import com.neueda.url.shortner.model.HitMetric;
import com.neueda.url.shortner.services.StatisticsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StatisticsController {

    @Resource
    StatisticsService statisticsService;

    @RequestMapping(value = "/stats/{link_id}", method = RequestMethod.GET)
    public List<HitMetric> stats(@PathVariable("link_id") int linkId) {
        return statisticsService.findByLinkId(linkId);
    }
}
