package lecturemanagement;

public class DataTransferProtocol {

    public static final String DbName = "lecturedb";
    public static final String ServerAddress = "10.0.0.1";
    public static final String IPServer = "";
    public static final int SignUpPort = 7001;
    public static final int QuizSlideNumberPort = 7002;
    public static final int ChatPort = 7003;
    public static final int StartLecturePort = 7007;
    public static final int SignInOutPort = 7008;

    /* 
    port  : 7008  signInSignOutPort
    Protocol username
   -> Sign in 
    Pattern : "username-password" UTF ---> and server will answer with 1 if correct data  , 
    0 if incorrect data , if 2 already login user
   -> Sign out 
    Pattern : "username" UTF ----> if removed send 1 if not send 0 
     */
 /*
    port : 7001 SignUpPort 
    when client click sign up   
    pattern : "StudentTransfer" object --->  and server will answer with 1 if the doctor accepted him 
    ,  answer 0 if refused and answer 2 if data is already used
     */
 /*
     port : 7007 StartLecturePort 
    when doctor want to share his slide after upload it  
    student will conncet with server when( student login ) 
    server send to client pattern : "lectureName" UTF  and  "lectureFile" file ----> must client connect to server after login screen  
    server will send also the slide number .
    
     */
 /*
        port : 7007 StartLecturePort      
           server send slide number pattern "number" 
            server send start lecture Marker to start quiz and send quiz then send end quiz 
            client will replay with username-grade pattern
     */
 /*
    ChatPort 7003
    client send Message pattern "username-Message"
    server send reply with pattern "replay"
     */
 /*
   QuizSlideNumberPort 7002
   slide : Server send Message pattern "slideNumber" 
   Quiz :  Server send first Start with
              QuizMarker -> StartTime -> QuizObject -> EndMarker   
   QuizTerminator : Server Send QuizTerminator 
     if want get grades  -> pattern "QuizTerminatorMarker-1"
     if he doesn't want -> pattern "QuizTerminatorMarker-0"
     */
}
