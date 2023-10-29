package onlinetestapi.ControllerClasses;


import onlinetestapi.Dto.QuestionNameAndIdDto;
import onlinetestapi.Dto.UserDto;
import onlinetestapi.EntityClasses.Question;
import onlinetestapi.EntityClasses.User;
import onlinetestapi.ServiceClasses.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    final TestService testService;

    @Autowired
    public AdminController(TestService testService){
        this.testService=testService;
    }


    //add user to Db
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        User u=testService.addUser(user);
        if(u==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Name already exist:"+user.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Created with username:"+user.getName());
    }

    //add new Question/Questions to DB
    @PostMapping("/add-question")
    public ResponseEntity<String> addQuestion(@RequestBody List<Question> addQuestions){
        if(testService.addQuestions(addQuestions)){
            return ResponseEntity.status(HttpStatus.CREATED).body(addQuestions.size()+" Questions Successfully added");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unable to add the questions because of error");
    }

    //delete the question  accepts query url
    //name only given-> delete by name
    //id only given-> delete by id
    //name and id given -> both id and name should belong to same question otherwise it won't delete question
    @DeleteMapping("delete-question")
    public ResponseEntity<String> deleteQuestion(@RequestParam(value="name",required = false) String name,
                                                 @RequestParam(value="id",required = false,defaultValue = "-1") long id){
        if(id==-1 && name==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT Found");
        else if(name==null){
            if(testService.deleteQuestionById(id)){
                return ResponseEntity.status(HttpStatus.OK).body("Question Deleted with id:"+id);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT Found");
        }
        else if(id==-1){
            if(testService.deleteQuestionByName(name)){
                return ResponseEntity.status(HttpStatus.OK).body("Question Deleted with name:"+name);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT Found");
        }
        if(testService.deleteQuestionByIdAndName(id,name)){
            return ResponseEntity.status(HttpStatus.OK).body("Question Deleted with name:"+name);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to Delete the Question Caused by question not found with given id and name");
    }

    //delete the user by username need to give the username as query url
    @DeleteMapping("delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam("username") String userName){
        int res=testService.deleteUserByName(userName);
        if(res==-1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        else if(res==1){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted user:"+userName);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unable to delete the user:"+userName);
    }

    //gives the users details by username,his/her score total score,best score or by role and can apply the sort
    @GetMapping("/get-users-details")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value="username",required = false) String userName,
                                                     @RequestParam(value="total-score",required = false,defaultValue = "-2") long totalScore,
                                                     @RequestParam(value="best-score",required = false,defaultValue = "-2") long bestScore,
                                                     @RequestParam(value="sort",required = false,defaultValue = "-2") int sort,
                                                     @RequestParam(value="role",required = false) String role
            ){
        if(userName==null && totalScore==-2 && bestScore==-2){
            return ResponseEntity.status(HttpStatus.OK).body(testService.getAllUsers());
        }
        else if(userName!=null) {
            UserDto res=testService.getUserByName(userName);
            if(res==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(List.of(res));
        }
        else if(role!=null){
            return ResponseEntity.status(HttpStatus.OK).body(testService.getUsersByRole(role));
        }
        else if(totalScore!=-2 && bestScore==-2){
            if(sort==1)
                return ResponseEntity.status(HttpStatus.OK).body(testService.getUsersByTotalScoreGreaterThanScore(totalScore));
            return ResponseEntity.status(HttpStatus.OK).body(testService.getUsersByTotalScoreLessThanScore(totalScore));
        }
        else if(bestScore!=-2 && totalScore==-2){
            if(sort==1)
                return ResponseEntity.status(HttpStatus.OK).body(testService.getUsersByBestScoreGreaterThanScore(bestScore));
            return ResponseEntity.status(HttpStatus.OK).body(testService.getUsersByBestScoreLessThanScore(bestScore));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    //updating the user
    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody User user){

        int update=testService.updateUser(user);
        if(update==-1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user Not Found with name"+user.getName());
        else if (update==0)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to update the user");
        return ResponseEntity.status(HttpStatus.OK).body("user details updated");
    }

    //updating the question

    @GetMapping("get-question-list")
    public ResponseEntity<List<QuestionNameAndIdDto>> getQuestionNames() {
        return ResponseEntity.status(HttpStatus.OK).body(testService.getQuestionNamesAndIds());
    }
    @PutMapping("/update-question")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question){
        int update=testService.updateQuestion(question);
        System.out.println("requested:"+question);
        if(update==-1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question Not Found with name"+question.getQuestionName());
        else if (update==0)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to update the Question");
        return ResponseEntity.status(HttpStatus.OK).body("Question Updated updated");

    }
}
