class Question{
  private String questionText;
  private String choice1;
  private String choice2;
  private String choice3;
  private String choice4;
  private int correctAnswer;
  private int points;
//Constructuctor
  Question() {
    questionText ="";
    choice1 = "";
    choice2 = "";
    choice3 = "";
    choice4 = "";
    correctAnswer = 0;
    points = 0;

  }
  //Constructuctor 
  Question(String aQuestionText, String aChoice1, String aChoice2, String aChoiceC, String aChoiceD, int aCorrectAnswer, int aPoints) {
    questionText = aQuestionText;
    choice1 = aChoice1;
    choice2 = aChoice2;
    choice3 = aChoice3;
    choice4 = aChoice4;
    correctAnswer = aCorrectAnswer;
    points = aPoints;

  }
  
  String getQuestionText() {
    return questionText;
  }

  String getChoice1() {
    return choice1;
  }

  String getChoice2() {
    return choice2;
  }

  String getChoice3() {
    return choice3;
  }
  String getChoice4() {
    return choice4;
  }

  int getCorrectAnswer(){
    return correctAnswer;
  }

  int getPoints(){
    return points;
  }

}