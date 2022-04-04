package com.kodilla.ecommercee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Getter
@NoArgsConstructor
public class GroupDto {

    private Long groupId;
    private String groupName;

    public GroupDto(Long id, String name) {
        this.groupId = id;
        this.groupName = name;
    }
}