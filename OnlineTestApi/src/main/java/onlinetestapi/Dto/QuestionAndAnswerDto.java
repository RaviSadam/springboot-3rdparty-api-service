package onlinetestapi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionAndAnswerDto {
    private long id;
    private String description;
    private String questionName;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
    private String userOption;
}
