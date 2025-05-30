package com.steampals.steampals.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    private long id;
    private boolean usuario1like;
    private boolean usuario2like;
    private boolean completo;
}
