package com.not.pocFileExplorer.components.modules.infra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Integer company;

    private String description;

    private Integer type;

    private String url;

    private Integer icon;

    private Integer parentId;

    private String file;

    private Boolean status;

    private Boolean deleted;
}
