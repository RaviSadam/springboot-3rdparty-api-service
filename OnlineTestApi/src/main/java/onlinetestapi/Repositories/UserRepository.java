package onlinetestapi.Repositories;

import onlinetestapi.EntityClasses.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    //gives all user from DB
    @Query(value="SELECT name,tests_taken,best_score,total_score FROM user ORDER BY name,total_score",nativeQuery = true)
    public List<User> getAllUsers();

    //gives only user by name
    @Query(value="SELECT * FROM user WHERE name=:user_name",nativeQuery = true)
    public User getUserByName(@Param("user_name") String name);

    //gives the user with greater score than given score
    @Query(value="SELECT * FROM user WHERE total_score>=:score ORDER BY total_score",nativeQuery = true)
    public List<User> getUsersByTotalScoreGreaterScore(@Param("score") long score);

    //gives the user with lower score than given score
    @Query(value="SELECT * FROM user WHERE total_score<=:score ORDER BY total_score",nativeQuery = true)
    public List<User> getUsersByTotalScoreLessScore(@Param("score") long score);

    //gives users by best score greater than score
    @Query(value="SELECT * FROM user WHERE best_score>=:score ORDER BY best_score",nativeQuery = true)
    public List<User> getUsersByBestScoreGreaterThanScore(@Param("score") long score);

    //gives users by best score less than score
    @Query(value="SELECT * FROM user WHERE best_score<=:score ORDER BY best_score",nativeQuery = true)
    public List<User> getUsersByBestScoreLessThanScore(@Param("score") long score);

    //gives users by role
    @Query(value="SELECT * FROM user WHERE role=:role ORDER BY role,name",nativeQuery = true)
    public List<User> getUsersByRole(@Param("role") String role);
}