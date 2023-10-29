package onlinetestapi.ControllerClasses;

import onlinetestapi.Dto.QuestionAndAnswerDto;
import onlinetestapi.Dto.IdAndUserResponseDto;
import onlinetestapi.Dto.UserDto;
import onlinetestapi.EntityClasses.Question;
import onlinetestapi.ServiceClasses.TestService;
import org.hibernate.query.sqm.sql.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

    final TestService testService;

    @Autowired
    UserController(TestService testService){
        this.testService=testService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/get-details/{username}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable String username){
        UserDto result = testService.getUserByName(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/get-questions")
    public ResponseEntity<List<Question>> getQuestions(@RequestParam(value="language",required = false) String language,
                                                       @RequestParam(value="difficulty",required = false) String difficulty,
                                                       @RequestParam(value="sort",required = false,defaultValue ="0") int sortByLikes){
        if(language==null && difficulty==null){
            return ResponseEntity.status(HttpStatus.OK).body(testService.getAllQuestions(sortByLikes));
        }
        else if(language!=null && difficulty!=null){
            return ResponseEntity.status(HttpStatus.OK).body(testService.getQuestionsByLanguageAndDifficulty(language,difficulty,sortByLikes));
        }
        else if(language == null){
            return ResponseEntity.status(HttpStatus.OK).body(testService.getQuestionsByDifficulty(difficulty,sortByLikes));
        }
        return ResponseEntity.status(HttpStatus.OK).body(testService.getQuestionsByLanguage(language,sortByLikes));
    }


    @PostMapping("/validate-answers")
    public ResponseEntity<List<QuestionAndAnswerDto>> validateAnswers(@RequestBody List<IdAndUserResponseDto> userResponse){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(testService.validateAnswers(userResponse));
    }

    //exceptions and error handler code
    @ExceptionHandler(InternalError.class)
    public ResponseEntity<InternalError> internalError(InternalError error){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<ConversionException> conversionExceptionResponseEntity(ConversionException error){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> exception(Exception e){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
    }
}
