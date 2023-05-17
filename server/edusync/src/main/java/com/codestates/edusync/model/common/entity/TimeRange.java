package com.codestates.edusync.model.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
//@Setter   // 객체 참조를 통해 수정하면 안되기에, 세터는 절대 사용하면 안된다. 내용을 불변으로 만들고, 생성자를 통해서만 생성할 수 있도록 한다.
@NoArgsConstructor
@Embeddable
public class TimeRange {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
}
