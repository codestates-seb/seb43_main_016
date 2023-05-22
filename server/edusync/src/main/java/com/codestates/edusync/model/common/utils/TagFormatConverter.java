package com.codestates.edusync.model.common.utils;

import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;

import java.util.*;

public interface TagFormatConverter {
    static List<SearchTag> mapToList(Map<String, Set<String>> tagMap, Studygroup studygroup) {
        List<SearchTag> result = new ArrayList<>();
        tagMap.forEach((key, values) -> {
            for( String value : values ) {
                SearchTag resultTag = new SearchTag();
                resultTag.setTagKey(key);
                resultTag.setTagValue(value);
                if( studygroup.getId() != null )
                    resultTag.setStudygroup(studygroup);

                result.add(resultTag);
            }
        });
        return result;
    }

    static Map<String, Set<String>> listToMap(List<SearchTag> tags) {
        Map<String, Set<String>> resultTags = new HashMap<>();
        for( SearchTag tag : tags) {
            String key = tag.getTagKey();
            String value = tag.getTagValue();
            Set<String> resultValues = new HashSet<>();
            if(resultTags.get(key) != null) {
                resultValues.addAll(resultTags.get(key));
            }
            resultValues.add(value);
            resultTags.put(key, resultValues);
        }

        return resultTags;
    }
}
