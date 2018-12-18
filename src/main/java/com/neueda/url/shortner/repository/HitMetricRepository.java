package com.neueda.url.shortner.repository;


import com.neueda.url.shortner.model.HitMetric;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HitMetricRepository extends CrudRepository<HitMetric, Integer> {

    List<HitMetric> findByLinkId(Integer linkId);
}
