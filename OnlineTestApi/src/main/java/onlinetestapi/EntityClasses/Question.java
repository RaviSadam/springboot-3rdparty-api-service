package onlinetestapi.EntityClasses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="question")
public class Question {
    @Id
    @SequenceGenerator(
            name="question_id_generator",
            sequenceName = "question_id_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_id_generator"
    )
    private long id;

    @Column(name="name",unique = true)
    private String questionName;

    @Column(name="description",columnDefinition = "TEXT")
    private String description;

    @Column(name="option1",columnDefinition = "TEXT")
    private String option1;

    @Column(name="option2",columnDefinition = "TEXT")
    private String option2;

    @Column(name="option3",columnDefinition = "TEXT")
    private String option3;

    @Column(name="option4",columnDefinition = "TEXT")
    private String option4;

    @Column(name="likes",columnDefinition = "INT DEFAULT 0")
    private long likes;

    @Column(name="correct_option",length = 3)
    private String correctOption;

    @Column(name="language",length=30)
    private String language;

    @Column(name="difficulty",length=10,columnDefinition = "VARCHAR(10) DEFAULT Easy")
    private String difficulty;
}
