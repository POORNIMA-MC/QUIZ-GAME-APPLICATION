import java.util.*;
import java.util.concurrent.TimeUnit;
class Question {
    private final String question;
    private final String[] choices;
    private final String correctAnswer;
    private final int questionValue;
    private boolean usedFiftyFifty = false;
    private boolean usedAudiencePoll = false;
    private boolean usedSkip = false;

    public Question(String question, String[] choices, String correctAnswer, int questionValue) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.questionValue = questionValue;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getQuestionValue() {
        return questionValue;
    }

    public boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    public void useFiftyFifty() {
        if (!usedFiftyFifty) {
            // Implement logic to eliminate two incorrect answer choices
            usedFiftyFifty = true;
        }
    }

    public boolean checkAnswerWithFiftyFifty(String answer) {
        // Implement logic to check the answer after using the 50:50 lifeline
        return correctAnswer.equalsIgnoreCase(answer);
    }

    public void useAudiencePoll() {
        if (!usedAudiencePoll) {
            // Implement logic to simulate the audience poll lifeline
            usedAudiencePoll = true;
        }
    }

    public Map<String, Integer> getAudiencePollResults() {
        if (usedAudiencePoll) {
            Map<String, Integer> results = new HashMap<>();
            int correctPercentage = 0;

            // Determine the correct percentage based on the question
            String questionText = getQuestion();
            for (Question question : allQuestions) {
                if (question.getQuestion().equals(questionText)) {
                    correctPercentage = 80; // You can customize the percentage for the correct answer
                } else {
                    correctPercentage = 5; // You can customize the percentage for other options
                }

                // Calculate percentages for each choice
                String[] choices = question.getChoices();
                for (String choice : choices) {
                    results.put(choice.substring(0, 1), choice.contains(question.getCorrectAnswer()) ? correctPercentage : 100 - correctPercentage);
                }
            }

            return results;
        } else {
            return null; // Audience poll results are not available until the lifeline is used
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getPossibleCorrectAnswers() {
        if (usedFiftyFifty) {
            // Implement logic to get the two possible correct answers after using the 50:50 lifeline
            String option1 = "A".equalsIgnoreCase(correctAnswer) ? "B" : "A";
            String option2 = "D".equalsIgnoreCase(correctAnswer) ? "C" : "D";
            return option1 + " and " + option2;
        } else {
            return "You haven't used the 50:50 lifeline yet.";
        }
    }

    public boolean isUsedFiftyFifty() {
        return usedFiftyFifty;
    }

    public boolean isUsedAudiencePoll() {
        return usedAudiencePoll;
    }

    public boolean isUsedSkip() {
        return usedSkip;
    }

    // Define a list of all questions
    static List<Question> allQuestions = new ArrayList<>();

    // Initialize the list with questions and their correct answers
    static {
        allQuestions.add(new Question("What is the capital of France?", new String[]{"A. London", "B. Paris", "C. Berlin", "D. Madrid"}, "B", 1000));
        allQuestions.add(new Question("Which planet is known as the Red Planet?", new String[]{"A. Venus", "B. Earth", "C. Mars", "D. Jupiter"}, "C", 2000));
        allQuestions.add(new Question("What is the largest mammal in the world?", new String[]{"A. Elephant", "B. Blue Whale", "C. Giraffe", "D. Lion"}, "B", 5000));
        allQuestions.add(new Question("Which gas do plants absorb from the atmosphere?", new String[]{"A. Oxygen", "B. Carbon Dioxide", "C. Nitrogen", "D. Hydrogen"}, "B", 10000));
        allQuestions.add(new Question("What is the currency of Japan?", new String[]{"A. Yen", "B. Euro", "C. Dollar", "D. Pound"}, "A", 20000));
        allQuestions.add(new Question("Which gas is responsible for the Earth's ozone layer?", new String[]{"A. Oxygen", "B. Hydrogen", "C. Carbon Dioxide", "D. Ozone"}, "D", 50000));
        allQuestions.add(new Question("Who wrote the play 'Romeo and Juliet'?", new String[]{"A. Charles Dickens", "B. William Shakespeare", "C. Leo Tolstoy", "D. Jane Austen"}, "B", 100000));
        allQuestions.add(new Question("Which country is known as the 'Land of the Rising Sun'?", new String[]{"A. China", "B. South Korea", "C. Japan", "D. India"}, "C", 200000));
        allQuestions.add(new Question("What is the chemical symbol for gold?", new String[]{"A. Au", "B. Ag", "C. Hg", "D. Ge"}, "A", 500000));
        allQuestions.add(new Question("Which planet is known as the 'Morning Star'?", new String[]{"A. Venus", "B. Mars", "C. Saturn", "D. Jupiter"}, "A", 1000000));
    }
}

class Lifelines {
    private boolean fiftyFiftyUsed = false;
    private boolean audiencePollUsed = false;
    private boolean skipUsed = false;

    public boolean isFiftyFiftyUsed() {
        return fiftyFiftyUsed;
    }

    public boolean isAudiencePollUsed() {
        return audiencePollUsed;
    }

    public boolean isSkipUsed() {
        return skipUsed;
    }

    public void useFiftyFifty(Question question) {
        if (!fiftyFiftyUsed) {
            fiftyFiftyUsed = true;
            question.useFiftyFifty();

            // Customize 50:50 elimination based on the correct answer
            String option1, option2;
            if (question.getCorrectAnswer().equals("A")) {
                option1 = "A";
                option2 = "C";
            } else if (question.getCorrectAnswer().equals("B")) {
                option1 = "B";
                option2 = "D";
            } else if (question.getCorrectAnswer().equals("C")) {
                option1 = "A";
                option2 = "C";
            } else {  // Assume the correct answer is "D"
                option1 = "B";
                option2 = "D";
            }

            System.out.println("You've used the 50:50 lifeline.");
            System.out.println("50:50 - Correct answer lies in: " + option1 + " and " + option2);

            String userChoice;

            while (true) {
                System.out.print("Choose the correct answer (" + option1 + " or " + option2 + ") or press 'O' for another lifeline: ");
                userChoice = KBC.scanner.nextLine().toUpperCase();

                if (userChoice.equals("O")) {
                    displayAvailableLifelines();
                    System.out.print("Choose another lifeline or press 'X' to Continue without a lifeline: ");
                    String lifelineChoice = KBC.scanner.nextLine().toUpperCase();

                    if (lifelineChoice.equals("X")) {
                        // Continue without using another lifeline
                        System.out.println("Continuing without a lifeline.");
                        break;
                    } else if (lifelineChoice.equals("AUDIENCE POLL") && !audiencePollUsed) {
                        useAudiencePoll(question);
                        break;
                    } else if (lifelineChoice.equals("SKIP") && !skipUsed) {
                        useSkip(question);
                        break;
                    } else {
                        System.out.println("Invalid lifeline choice. Please choose 'AUDIENCE POLL', 'SKIP', or press 'X' to Continue without a lifeline.");
                    }
                } else if (userChoice.equals(option1) || userChoice.equals(option2)) {
                    if (question.checkAnswer(userChoice)) {
                        System.out.println("Correct! You've won " + question.getQuestionValue() + " rupees.");
                        // Add the amount and proceed to the next question
                        break;
                    } else {
                        System.out.println("Sorry, that's incorrect. Your game ends here.");
                        // End execution and display the balance
                        System.exit(0);
                    }
                } else {
                    System.out.println("Invalid choice. Please choose between " + option1 + " and " + option2 + " or press 'O' for another lifeline.");
                }
            }
        } else {
            System.out.println("You've already used the 50:50 lifeline. Choose a different lifeline.");
        }
    }


    private void chooseAnotherLifeline(Question question) {
    }
    public void displayAvailableLifelines() {
        System.out.println("Available lifelines:");
        if (!audiencePollUsed) {
            System.out.println("1. Audience Poll");
        }
        if (!fiftyFiftyUsed) {
            System.out.println("2. 50:50");
        }
        if (!skipUsed) {
            System.out.println("3. Skip");
        }
    }

    private void chooseAnotherLifeline(Question question, String option1, String option2) {
        int availableLifelines=3;
        if (availableLifelines > 0) {
            System.out.print("Choose another lifeline (Audience Poll) or press 'X' to Continue without a lifeline: ");
            String lifelineChoice = KBC.scanner.nextLine().toUpperCase();

            if (lifelineChoice.equals("X")) {
                // Continue without using another lifeline
                System.out.println("Continuing without a lifeline.");
            } else if (lifelineChoice.equals("AUDIENCE POLL")) {
                if (!audiencePollUsed) {
                    useAudiencePoll(question);
                    availableLifelines--;
                } else {
                    System.out.println("You've already used the Audience Poll lifeline. Choose a different lifeline.");
                    chooseAnotherLifeline(question);
                }
            } else {
                System.out.println("Invalid lifeline choice. Please choose 'AUDIENCE POLL' or press 'X' to Continue without a lifeline.");
                chooseAnotherLifeline(question);
            }
        } else {
            System.out.println("You have no more lifelines available.");
            System.out.println("Invalid choice. Please choose between " + option1 + " and " + option2 + ".");
        }
    }

    public void useAudiencePoll(Question question) {
        if (!audiencePollUsed) {
            audiencePollUsed = true;
            question.useAudiencePoll();
            System.out.println("You've used the Audience Poll lifeline.");

            // Simulate audience poll results
            Map<String, Integer> audiencePollResults = question.getAudiencePollResults();

            // Generate a random percentage for the correct answer (between 40% and 70%)
            int correctAnswerPercentage = (int) (Math.random() * 31) + 40;

            // Assign the correct answer percentage to the correct answer choice
            audiencePollResults.put(question.getCorrectAnswer(), correctAnswerPercentage);

            // Generate random percentages for the remaining options while ensuring the total is 100%
            int totalPercentage = 100 - correctAnswerPercentage;
            List<String> choices = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
            choices.remove(question.getCorrectAnswer());

            for (String choice : choices) {
                int randomPercentage = (int) (Math.random() * totalPercentage);
                audiencePollResults.put(choice, randomPercentage);
                totalPercentage -= randomPercentage;
            }

            // The last choice will have the remaining percentage to ensure the total is 100%
            String lastChoice = choices.get(0);
            audiencePollResults.put(lastChoice, totalPercentage);

            System.out.println("Audience Poll Results:");
            for (String choice : audiencePollResults.keySet()) {
                System.out.println(choice + ": " + audiencePollResults.get(choice) + "%");
            }

            String userChoice;

            while (true) {
                System.out.print("Choose the correct answer based on audience poll (A, B, C, or D) or press 'O' for another lifeline: ");
                userChoice = KBC.scanner.nextLine().toUpperCase();

                if (userChoice.equals("O")) {
                    displayAvailableLifelines();
                    System.out.print("Choose another lifeline or press 'X' to Continue without a lifeline: ");
                    String lifelineChoice = KBC.scanner.nextLine().toUpperCase();

                    if (lifelineChoice.equals("X")) {
                        // Continue without using another lifeline
                        System.out.println("Continuing without a lifeline.");
                        break;
                    } else if (lifelineChoice.equals("50:50") && !fiftyFiftyUsed) {
                        useFiftyFifty(question);
                        break;
                    } else if (lifelineChoice.equals("SKIP") && !skipUsed) {
                        useSkip(question);
                        break;
                    } else {
                        System.out.println("Invalid lifeline choice. Please choose '50:50', 'SKIP', or press 'X' to Continue without a lifeline.");
                    }
                } else if (userChoice.equals(question.getCorrectAnswer())) {
                    System.out.println("Correct! You've won " + question.getQuestionValue() + " rupees.");
                    break;
                } else {
                    System.out.println("Sorry, that's incorrect. Your game ends here.");
                    skipUsed = true; // If the user got the audience poll answer wrong, prevent them from using the Skip lifeline
                    System.exit(0);
                }
            }
        } else {
            System.out.println("You've already used the Audience Poll lifeline. Choose a different lifeline.");
        }
    }



    public void useSkip(Question question) {
        if (!skipUsed) {
            skipUsed = true;
            System.out.println("You've used the Skip lifeline.");
            // Proceed to the next question without adding to the amount
        } else {
            System.out.println("You've already used the Skip lifeline. Choose a different lifeline.");
        }
    }
    // Other methods remain unchanged
}

public class KBC {
    static Scanner scanner = new Scanner(System.in);
    static Timer timer = new Timer();
    static int timeLimitSeconds = 15;

    public static void main(String[] args) {
        String candidateName, occupation;
        int availableLifelines = 3; // Initialize with 3 available lifelines

        System.out.println("Welcome to Koun Banega Karodpati!");
        System.out.print("Please enter your name: ");
        candidateName = scanner.nextLine();
        System.out.print("Please enter your occupation: ");
        occupation = scanner.nextLine();

        System.out.println("\nGame Rules:");
        displayGameRules();

        System.out.print("\nAre you ready to start the game? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            Lifelines lifelines = new Lifelines();

            startGame(candidateName, occupation, availableLifelines, lifelines);
        } else {
            System.out.println("Thank you for considering KBC. Goodbye!");
        }
    }

    public static void displayGameRules() {
        System.out.println("Game Rules:");
        System.out.println("1. There are 10 questions with increasing difficulty.");
        System.out.println("2. You have 3 lifelines: 50:50, Audience Poll, and Skip.");
        System.out.println("3. You can use each lifeline only once.");
        System.out.println("4. If you answer a question correctly, you win the specified amount of money.");
        System.out.println("5. If you answer incorrectly, you lose the game.");
    }

    public static void startGame(String candidateName, String occupation, int availableLifelines, Lifelines lifelines) {
        int score = 0;
        int questionNumber = 1;

        while (questionNumber <= 10) {
            Question currentQuestion = Question.allQuestions.get(questionNumber - 1);
            boolean continueToNextQuestion = true;

            System.out.println("Question " + questionNumber + ": " + currentQuestion.getQuestion());
            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choices.length; i++) {
                System.out.println(choices[i]);
            }

            if (questionNumber >= 5) {
                System.out.print("Choose a lifeline (50:50, Audience Poll) or press Q to Continue without a lifeline, or press X to Exit: ");
                String lifelineChoice = scanner.nextLine();

                if (lifelineChoice.equalsIgnoreCase("X")) {
                    System.out.println("Exiting the game.");
                    break;
                } else if (lifelineChoice.equalsIgnoreCase("50:50")) {
                    if (!lifelines.isFiftyFiftyUsed()) {
                        lifelines.useFiftyFifty(currentQuestion);
                        availableLifelines--;
                        continueToNextQuestion = false;
                    } else {
                        System.out.println("You've already used the 50:50 lifeline. Choose a different lifeline.");
                        continue;
                    }
                } else if (lifelineChoice.equalsIgnoreCase("Audience Poll")) {
                    if (!lifelines.isAudiencePollUsed()) {
                        lifelines.useAudiencePoll(currentQuestion);
                        availableLifelines--;
                        continueToNextQuestion = false;
                    } else {
                        System.out.println("You've already used the Audience Poll lifeline. Choose a different lifeline.");
                        continue;
                    }
                } else if (lifelineChoice.equalsIgnoreCase("Q")) {
                    // Continue without using a lifeline
                } else {
                    System.out.println("Invalid lifeline choice. Please choose 50:50, Audience Poll, or Q to Continue.");
                    continue;
                }
            } else {
                System.out.print("Choose a lifeline (50:50, Audience Poll, Skip) or press Q to Continue without a lifeline: ");
                String lifelineChoice = scanner.nextLine();

                if (lifelineChoice.equalsIgnoreCase("50:50")) {
                    if (!lifelines.isFiftyFiftyUsed()) {
                        lifelines.useFiftyFifty(currentQuestion);
                        availableLifelines--;
                        continueToNextQuestion = false;
                    } else {
                        System.out.println("You've already used the 50:50 lifeline. Choose a different lifeline.");
                        continue;
                    }
                } else if (lifelineChoice.equalsIgnoreCase("Audience Poll")) {
                    if (!lifelines.isAudiencePollUsed()) {
                        lifelines.useAudiencePoll(currentQuestion);
                        availableLifelines--;
                        continueToNextQuestion = false;
                    } else {
                        System.out.println("You've already used the Audience Poll lifeline. Choose a different lifeline.");
                        continue;
                    }
                } else if (lifelineChoice.equalsIgnoreCase("Skip")) {
                    if (!lifelines.isSkipUsed()) {
                        lifelines.useSkip(currentQuestion);
                        availableLifelines--;
                        System.out.println("You've chosen to skip this question.");
                        continueToNextQuestion = false;
                    } else {
                        System.out.println("You've already used the Skip lifeline. Choose a different lifeline.");
                        continue;
                    }
                } else if (lifelineChoice.equalsIgnoreCase("Q")) {
                    // Continue without using a lifeline
                } else {
                    System.out.println("Invalid lifeline choice. Please choose 50:50, Audience Poll, Skip, or Q to Continue.");
                    continue;
                }
            }

            if (continueToNextQuestion) {
                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine().toUpperCase();

                if (currentQuestion.checkAnswer(userAnswer)) {
                    System.out.println("Correct! You've won " + currentQuestion.getQuestionValue() + " rupees.");
                    score += currentQuestion.getQuestionValue();
                } else {
                    System.out.println("Sorry, that's incorrect. Your game ends here.");
                    System.out.println("Your total score: " + score + " rupees.");
                    System.out.println("Thank you for playing, " + candidateName + "!");
                    System.exit(0);
                }
            }

            questionNumber++;
        }

        System.out.println("Your total score: " + score + " rupees.");
        System.out.println("Thank you for playing, " + candidateName + "!");
    }



}