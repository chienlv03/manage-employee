package com.globits.da.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_commune")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne()
    @JoinColumn(name = "district_id", nullable = false)
    @JsonBackReference
    private District district;
}
