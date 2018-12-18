package com.neueda.url.shortner.services;

import com.neueda.url.shortner.model.HitMetric;
import com.neueda.url.shortner.model.Link;
import com.neueda.url.shortner.repository.HitMetricRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class StatisticsService {

    @Resource
    HitMetricRepository hitMetricRepository;

    public List<HitMetric> findByLinkId(Integer linkId) {
        return hitMetricRepository.findByLinkId(linkId);
    }

    @Async
    public void saveHitMetric(Link link, String clientInfo) {
        hitMetricRepository.save(
                HitMetric.builder()
                        .id(null)
                        .link(link)
                        .hitTimestamp(new java.sql.Timestamp(new Date().getTime()))
                        .clientInfo(clientInfo)
                        .build());
    }

}
