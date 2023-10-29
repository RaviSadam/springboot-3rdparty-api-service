package onlinetestapi.ServiceClasses;

import onlinetestapi.Dto.QuestionAndAnswerDto;
import onlinetestapi.Dto.IdAndUserResponseDto;
import onlinetestapi.Dto.QuestionNameAndIdDto;
import onlinetestapi.Dto.UserDto;
import onlinetestapi.EntityClasses.Question;
import onlinetestapi.EntityClasses.User;
import onlinetestapi.Repositories.QuestionRepository;
import onlinetestapi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class TestService {

    //cnostructor dependency injection
    final QuestionRepository questionRepository;
    final UserRepository userRepository;
//    final  PasswordEncoder passwordEncoder;


    @Autowired
    TestService(QuestionRepository questionRepository,UserRepository userRepository){
        this.questionRepository=questionRepository;
        this.userRepository=userRepository;
//        this.passwordEncoder=passwordEncoder;
    }

    //Question getter methods
    public List<Question> getAllQuestions(int sortByLikes){
        return this.sortData(questionRepository.getQuestions(),sortByLikes);
    }
    public List<Question> getQuestionsByLanguageAndDifficulty(String language,String difficulty,int sortByLikes){
        return this.sortData(questionRepository.getQuestionByLanguageAndDifficulty(language,difficulty),sortByLikes);
    }
    public List<Question> getQuestionsByLanguage(String language,int sortByLikes){
        return this.sortData(questionRepository.getQuestionsByLanguage(language),sortByLikes);
    }
    public List<Question> getQuestionsByDifficulty(String difficulty,int sortByLikes){
        return this.sortData(questionRepository.getQuestionsByDifficulty(difficulty),sortByLikes);
    }

    //user getter methods
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(this::toUserDto)
                .collect(toList());
    }

    //get user by name only one user
    public UserDto getUserByName(String name){
        return this.toUserDto(userRepository.getUserByName(name));
    }

    //get user by total score
    public List<UserDto> getUsersByTotalScoreGreaterThanScore(long score){
        return userRepository.getUsersByTotalScoreGreaterScore(score)
                .stream()
                .map(this::toUserDto)
                .collect(toList());
    }
    public List<UserDto> getUsersByTotalScoreLessThanScore(long score){
        return userRepository.getUsersByTotalScoreLessScore(score)
                .stream()
                .map(this::toUserDto)
                .collect(toList());
    }

    // get user by best score
    public List<UserDto> getUsersByBestScoreGreaterThanScore(long score){
        return userRepository.getUsersByBestScoreGreaterThanScore(score)
                .stream()
                .map(this::toUserDto)
                .collect(toList());
    }
    public List<UserDto> getUsersByBestScoreLessThanScore(long score){
        return userRepository.getUsersByBestScoreLessThanScore(score)
                .stream()
                .map(this::toUserDto)
                .collect(toList());
    }

    //get user by role
    public List<UserDto> getUsersByRole(String role){
        return userRepository.getUsersByRole(role)
                .stream()
                .map(this::toUserDto)
                .collect(toList());
    }
    public User addUser(User user){
        User dto=userRepository.getUserByName(user.getName());
        if(dto==null)
            return userRepository.save(user);
        else
            return null;
    }

    //validating the user responce
    public List<QuestionAndAnswerDto> validateAnswers(List<IdAndUserResponseDto> userResponse) {
        List<Long> questionIds=new ArrayList<>();
        Map<Long,String> mapper=new HashMap<>();
        for(IdAndUserResponseDto idAndUserResponseDto :userResponse){
            questionIds.add(idAndUserResponseDto.getId());
            mapper.put(idAndUserResponseDto.getId(), idAndUserResponseDto.getUserOption());
        }
        List<QuestionAndAnswerDto> result=new ArrayList<>();
        for(Question question:questionRepository.getQuestionIdAndCorrectOptionById(questionIds)){
            result.add(this.toQuestionAndAnswerDto(question,mapper.get(question.getId())));
        }
        return result;
    }

    //Sort questions by likes
    private List<Question> sortData(List<Question> questions,int sortByLikes){
        if(sortByLikes==-1)
            questions.sort(Comparator.comparing(Question::getLikes));
        else if(sortByLikes==1)
            questions.sort(Comparator.comparing(Question::getLikes).reversed());
        else
            questions.sort(Comparator.comparing(Question::getId));
        return questions;
    }

    //add question to db
    public boolean addQuestions(List<Question> addQuestions) {
        try {
            questionRepository.saveAll(addQuestions);
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }


    //deleting user
    public int deleteUserByName(String userName) {
        User u=userRepository.getUserByName(userName);
        if(u!=null) {
            userRepository.delete(u);
            return 1;
        }
        return -1;
    }

    public boolean deleteQuestionById(long id) {
        Question question=questionRepository.findById(id);
        if(question==null){
            return false;
        }
        questionRepository.delete(question);
        return true;
    }
    public boolean deleteQuestionByName(String name){
        Question question=questionRepository.findByQuestionName(name);
        if(question==null){
            return false;
        }
        questionRepository.delete(question);
        return true;
    }
    public boolean deleteQuestionByIdAndName(long id,String name){
        Question question=questionRepository.findByIdAndQuestionName(id,name);
        if(question==null){
            return false;
        }
        questionRepository.delete(question);
        return true;
    }

    public int updateUser(User temp) {
        User user=userRepository.getUserByName(temp.getName());
        if(user==null)
            return -1;
        try{
            if(temp.getPassword()!=null)
                user.setPassword(temp.getPassword());
            else if (temp.getRole()!=null)
                user.setRole(temp.getRole());
            userRepository.save(user);
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }

    public int updateQuestion(Question temp) {
        Question question=questionRepository.findByQuestionName(temp.getQuestionName());
        System.out.println(question.toString());
        if(question==null)
            return -1;
        try {
            if (temp.getDescription() != null)
                question.setDescription(temp.getDescription());
            if (temp.getOption1() != null)
                question.setOption1(temp.getOption1());
            if (temp.getOption2()!= null)
                question.setOption2(temp.getOption2());
            if (temp.getOption3()!= null)
                question.setOption3(temp.getOption3());
            if (temp.getOption4()!= null)
                question.setOption4(temp.getOption4());
            if (temp.getCorrectOption()!= null)
                question.setCorrectOption(temp.getCorrectOption());
            if (temp.getLanguage() != null)
                question.setLanguage(temp.getLanguage());
            if (temp.getDifficulty()!= null)
                question.setDifficulty(temp.getDifficulty());
            questionRepository.save(question);
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }

    //converting the User to UserDto
    private UserDto toUserDto(User user){
        if(user==null)
            return null;
        UserDto userDto=new UserDto();
        userDto.setName(user.getName());
        userDto.setTestTaken(user.getTestTaken());
        userDto.setBestScore(user.getBestScore());
        userDto.setTotalScore(user.getTestTaken());
        userDto.setRole(user.getRole());
        return userDto;
    }
    private QuestionAndAnswerDto toQuestionAndAnswerDto(Question question,String userOption){
        if(question==null)
            return null;
        QuestionAndAnswerDto questionAndAnswerDto=new QuestionAndAnswerDto();
        questionAndAnswerDto.setId(question.getId());
        questionAndAnswerDto.setQuestionName(question.getQuestionName());
        questionAndAnswerDto.setDescription(question.getDescription());
        questionAndAnswerDto.setOption1(question.getOption1());
        questionAndAnswerDto.setOption2(question.getOption2());
        questionAndAnswerDto.setOption3(question.getOption3());
        questionAndAnswerDto.setOption4(question.getOption4());
        questionAndAnswerDto.setCorrectOption(question.getCorrectOption());
        questionAndAnswerDto.setUserOption(userOption);
        return questionAndAnswerDto;
    }

    private QuestionNameAndIdDto toQuestionNameAndIdDto(Question question){
        if(question==null)
            return null;
        return new QuestionNameAndIdDto(question.getId(),question.getQuestionName());
    }

    public List<QuestionNameAndIdDto> getQuestionNamesAndIds() {
        List<Question> res=questionRepository.findAll();
        return res.stream().map(this::toQuestionNameAndIdDto).collect(toList());
    }
}
