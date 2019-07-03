package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2019/5/8.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceObject {
    private String type;
    private Double [] coordinates;
}
