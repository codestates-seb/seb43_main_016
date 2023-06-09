package com.codestates.edusync.model.study.studygroup.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StudygroupGetOrder {
    STUDYGROUP_GET_ORDER_BY_ID("기본값", "id"),
    STUDYGROUP_GET_ORDER_BY_MODIFIED_AT("수정순", "modifiedAt"),
    STUDYGROUP_GET_ORDER_BY_CATEGORY("카테고리순", "searchTags.tagKey"),
    STUDYGROUP_GET_ORDER_BY_RECRUITED("모집순", "isRecruited"),
    ;

    private final @Getter String order;
    private final @Getter String variable;

    public static StudygroupGetOrder valueOfOrder(String order) {
        for (StudygroupGetOrder sgo : StudygroupGetOrder.values()) {
            if (sgo.order.equals(order)) {
                return sgo;
            }
        }
        throw new IllegalArgumentException("[Studygroup get] Invalid order: " + order);
    }
}