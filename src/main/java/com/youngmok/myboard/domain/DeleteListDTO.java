package com.youngmok.myboard.domain;


import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DeleteListDTO  {
    private ArrayList<Integer> deleteList;

}
