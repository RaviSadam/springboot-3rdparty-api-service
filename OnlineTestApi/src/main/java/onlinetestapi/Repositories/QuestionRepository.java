package onlinetestapi.Repositories;

import onlinetestapi.EntityClasses.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question,Long> {

    //gives the questions by difficulty and language in random order
    @Query(value = "SELECT * FROM question WHERE difficulty=:level AND language=:language ORDER BY RAND() LIMIT 20",nativeQuery = true)
    public List<Question> getQuestionByLanguageAndDifficulty(@Param("language") String language, @Param("level") String level);

    //gives the questions by language in random order
    @Query(value="SELECT * FROM question WHERE language=:language ORDER BY RAND() LIMIT 20",nativeQuery = true)
    public List<Question> getQuestionsByLanguage(@Param("language") String language);

    //gives the questions by difficulty in random order
    @Query(value="SELECT * FROM question WHERE difficulty=:level ORDER BY RAND() LIMIT 20",nativeQuery = true)
    public List<Question> getQuestionsByDifficulty(@Param("level") String level);

    //gives all question
    @Query(value="SELECT * FROM question ORDER BY RAND() LIMIT 20",nativeQuery = true)
    public  List<Question> getQuestions();

    @Query(value="SELECT * FROM online_exam_api.question WHERE id IN :questionIds",nativeQuery = true)
    public List<Question> getQuestionIdAndCorrectOptionById(@Param("questionIds") List<Long> questionIds);


    public Question findByQuestionName(String name);
    public Question findById(long id);
    public Question findByIdAndQuestionName(long id,String name);
    public List<Question> findAll();

}
