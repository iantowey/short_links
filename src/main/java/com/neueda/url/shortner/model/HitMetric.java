package com.neueda.url.shortner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(schema="short_links", name = "HIT_METRICS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitMetric implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private Timestamp hitTimestamp;
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String clientInfo;
    @ManyToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    @JsonIgnore
    private Link link;


}
