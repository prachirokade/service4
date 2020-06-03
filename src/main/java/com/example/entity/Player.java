package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author prokade
 */
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    private Integer id;

    @Column
    private Integer parentId;

    @Column
    private String name;

    @Column
    private String color;

    /*@ManyToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private Player player;*/
}
