import java.awt.event.*; 
import java.awt.*;
import javax.swing.*; 
import java.io.*;
import java.util.ArrayList;

//setting up the labels buttuns and panels names 
class Game implements ActionListener {
  JLabel jlabQuestionPoint;
  JLabel jlabIntro, jlabEnterName;
  JTextField field; 
  JLabel jlabCurrent;
  JButton jButSubmitScore, jButRestart, jButNewQuestion, jButSubmitName;
  JLabel prompt;
  ArrayList<JButton> AnswerButtonList;
  ArrayList<Question> QuestionList;
  JFrame frame;
  JPanel panelA, panelB, panelC, panelD, panelE, panelF;
  JPanel jPanQuestions, jPanControls, jPanMain;
  int index, score;
  String strPlayerName;

  Game(){
    index = 0;
    score = 0;
    strPlayerName = "";
    AnswerButtonList = new ArrayList<JButton>();
// setting up a array list  to read the question from the data txt file  
    QuestionList = new ArrayList<Question>();
    try {
     FileReader myFile = new FileReader("Data.txt");
     BufferedReader reader = new BufferedReader(myFile);

      while (reader.ready()) {
        for (int i = 0;i<=4; i++) {
          String questionText = reader.readLine();
          String choice1 = reader.readLine();
          String choiceB = reader.readLine();
          String choiceC = reader.readLine();
          String choiceD = reader.readLine();
          String correctAnswerstr = reader.readLine();
          String pointsstr = reader.readLine();

          int correctAnswer = Integer.parseInt(correctAnswerstr);
          int points = Integer.parseInt(pointsstr);

          Question q = new Question(questionText, choice1, choiceB, choiceC, choiceD, correctAnswer, points);
          QuestionList.add(q);
        }
     }
      reader.close();
    } 

    catch (IOException exception) {
      System.out.println("An error occurred: " + exception);
    }
    // text to get the player started and to eneter their name
    jlabIntro = new JLabel("Lets get started! ");
    jlabEnterName = new JLabel("Please Enter Your Name:");
    jButSubmitName= new JButton("Submit Name");
    jButSubmitName.addActionListener(this);
    field = new JTextField(10); 
    field.setActionCommand("myTF");
    field.addActionListener(this);

// creating a Frame with title bar
    frame = new JFrame("Group number 2 trivia game!");
    frame.setLayout(new BorderLayout());
    frame.setSize(600, 300);
// getting the questions from data txt to appear 
    jlabQuestionPoint = new JLabel(QuestionList.get(index).getQuestionText()+ " (Point: " + QuestionList.get(index).getPoints()+ ")");
    
    prompt = new JLabel("");
  //creating the AnswerButtuns 
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoice1()));
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceB()));
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceC()));
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceD()));
    
    jlabCurrent = new JLabel("Score: " + score);
    
    jButNewQuestion= new JButton("New Question");
    jButNewQuestion.addActionListener(this); 
   
    jButRestart= new JButton("Restart the Game");
    jButRestart.addActionListener(this); 
     
    jButSubmitScore= new JButton("Submit your Score");
    jButSubmitScore.addActionListener(this); 


    panelA = new JPanel(new FlowLayout());
    panelB = new JPanel(new FlowLayout());
    panelC = new JPanel(new FlowLayout());
    panelD = new JPanel(new FlowLayout());
    panelE = new JPanel(new FlowLayout());
    panelF = new JPanel(new FlowLayout());
    jPanMain = new JPanel(new FlowLayout());
//adding to the panels
    panelA.add(jlabIntro);
    panelB.add(jlabEnterName);
    panelB.add(field);
    panelB.add(jButSubmitName);

    panelC.add(jlabQuestionPoint);
    
    for (int i = 0; i < 4; i++) {
      AnswerButtonList.get(i).addActionListener(this);
      panelD.add(AnswerButtonList.get(i));
    }

    panelE.add(prompt);
    panelE.add(jlabCurrent);

    panelF.add(jButNewQuestion);
    panelF.add(jButSubmitScore);
    jButSubmitScore.setVisible(false);
    panelF.add(jButRestart);

    jPanMain.add(panelA);
    jPanMain.add(panelB);
    jPanMain.add(panelC);
    jPanMain.add(panelD);
    jPanMain.add(panelE);
    jPanMain.add(panelF);

    frame.add(jPanMain);
    
    frame.setVisible(true); 

  }

public void actionPerformed(ActionEvent ae) { 
      String answerForQuestion = AnswerButtonList.get(QuestionList.get(index).getCorrectAnswer()-1).getText();
      System.out.println(answerForQuestion);
      
      if(ae.getActionCommand().equals(answerForQuestion)) {
        prompt.setText("You are correct");
        score+=QuestionList.get(index).getPoints();
        jlabCurrent.setText("score: " + score);
        nextQuestion();
            
      }
      else if(ae.getActionCommand().equals("Next Question")) {
        prompt.setText("");
        nextQuestion();
      }
      else if(ae.getActionCommand().equals("Restart Game")) {
        prompt.setText("");
        index = 0;
        score = 0;
        strPlayerName="";
        jlabEnterName.setText("Please Enter Your Name:");
        field.setVisible(true);
        field.setText("");
        jButSubmitName.setVisible(true);
        jlabCurrent.setText("score: " + score);
        jButNewQuestion.setVisible(true);
        jButSubmitScore.setVisible(false);
        panelA.setVisible(true);
        panelB.setVisible(true);
        panelC.setVisible(true);
        panelD.setVisible(true);
        panelE.setVisible(true);
        panelF.setVisible(true);
      }
      else if(ae.getActionCommand().equals("Submit Name")){
        strPlayerName = field.getText();
        jlabEnterName.setText("Welcome Player: "+ strPlayerName);
        field.setVisible(false);
        jButSubmitName.setVisible(false);
      }
      else if(ae.getActionCommand().equals("Submit Score")){
        recordScore();  
      }
      else {
        prompt.setText("Wrong Answer");
        nextQuestion();
      }
  }
  void recordScore(){

    FileWriter toWriteFile;
    try{
      toWriteFile = new FileWriter("Savedscores.txt",true);
      BufferedWriter output = new BufferedWriter(toWriteFile);
      output.write(strPlayerName);
      output.newLine();
      output.write(Integer.toString(score));
      output.newLine();
      output.flush();
      jButSubmitScore.setVisible(false);
    }
    catch (IOException excpt) { 
      excpt.printStackTrace(); 
    } 

  }

  void nextQuestion(){
      if (index<4){
        index = index + 1;
        System.out.println(index);

      }
      else{
        prompt.setText("Game Over! " + strPlayerName + " ");
        panelA.setVisible(false);
        panelB.setVisible(false);
        panelC.setVisible(false);
        panelD.setVisible(false);
        jButNewQuestion.setVisible(false);
        jButSubmitScore.setVisible(true);
         
      }
//getting more questions to show in the frame by clicking the answer buttuns 
    jlabQuestionPoint.setText(QuestionList.get(index).getQuestionText()+ " (Point: " + QuestionList.get(index).getPoints()+ ")");
        AnswerButtonList.get(0).setText(QuestionList.get(index).getChoice1());
        AnswerButtonList.get(1).setText(QuestionList.get(index).getChoiceB());
        AnswerButtonList.get(2).setText(QuestionList.get(index).getChoiceC());
        AnswerButtonList.get(3).setText(QuestionList.get(index).getChoiceD());
    }

    
  }
  