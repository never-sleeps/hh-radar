package ru.hh.radar.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "search_parameters")
public class SearchParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "area")
    private String area;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "text")
    private String text;

    @Column(name = "experience")
    private String experience;

    @Column(name = "employment")
    private String employment;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "order_by")
    private String orderBy = "publication_time";

    @Column(name = "page")
    private Long page = 1L;

    @Column(name = "per_page")
    private Long perPage = 3L;

    @Column(name = "paging_order")
    private String order = "desc";

    public void incrementPage() {
        this.page++;
    }

    public void resetPage() {
        this.page = 1L;
    }
}
