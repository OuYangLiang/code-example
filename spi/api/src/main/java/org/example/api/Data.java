package org.example.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author OuYang Liang
 * @since 2020-11-22
 */
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Data {
    private DataType type;
    private String content;
}
