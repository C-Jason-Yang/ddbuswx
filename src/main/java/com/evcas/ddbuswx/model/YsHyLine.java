package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by noxn on 2018/1/12.
 */

@Data
@AllArgsConstructor
@XmlRootElement(name = "root")
public class YsHyLine {
    List<Line> lines;
    public YsHyLine() {
        super();
    }
}
