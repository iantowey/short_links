package com.neueda.url.shortner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(schema="short_links",name = "LINKS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String shortPath;
    @Column(nullable = false)
    private String shortUrl;
    @Column(nullable = false)
    private Timestamp creationDate;
    @OneToMany(mappedBy = "link", targetEntity = HitMetric.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<HitMetric> hitMetrics;
}
