package onlinetestapi.EntityClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="user")
public class User {
    @Id
    @SequenceGenerator(
            name="user_id_generator",
            sequenceName = "user_id_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_generator"
    )
    private long id;

    @Column(name="name",length = 30)
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="role",length=15)
    private String role;

    @Column(name="tests_taken",columnDefinition = "INT DEFAULT 0")
    private long testTaken;

    @Column(name="best_score",columnDefinition = "INT DEFAULT 0")
    private long bestScore;

    @Column(name="total_score",columnDefinition = "INT DEFAULT 0")
    private long totalScore;
}
