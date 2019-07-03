package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by noxn on 2018/1/12.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "root")
public class YsHyStation {
    List<HyStation> lines;
}
