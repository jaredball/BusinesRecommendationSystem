
// Review class that encapsulates review data, including business, user, and review IDs,
// and provides functionality for tracking word frequency and presence in review text.

import java.util.Arrays;

public class Review {

    // Review Attributes
    private String business_id; // Unique identifier for the business associated with the review
    private String user_id; // Unique identifier for the user who wrote the review
    private String review_id; // Unique identifier for the review
    private double stars; // Rating given by the user (from 1 to 5 stars)
    private int useful; // Number of users who found the review useful
    private int funny; // Number of users who found the review funny
    private int cool; // Number of users who found the review cool
    private String text; // Text content of the review
    private String date; // Date when the review was posted
    private String business_name; // Name of the business associated with the review
    private int[] wordCount; // Array to store word counts for specific words in the review text
    private boolean[] containsWord; // Array to store word presence in the review text
    private double totalWeight; // Total weight assigned to the review

    // Getters for retrieving values of attributes
    public String getBusiness_id() {
        return business_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public double getStars() {
        return stars;
    }

    public int getUseful() {
        return useful;
    }

    public int getFunny() {
        return funny;
    }

    public int getCool() {
        return cool;
    }

    public String getReview_text() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public int[] getWordCount() {
        return wordCount;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public boolean[] getContainsWord() {
        return containsWord;
    }

    // Setters for updating values of attributes
    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setContainsWordTrue(int changeIndex) {
        this.containsWord[changeIndex] = true;
    }

    // Initializes frequency table with word counts and word presence
    public void initializeFrequencyTable(String[] words) {
        wordCount = new int[words.length]; // Array to store word counts
        Arrays.fill(wordCount, 0); // Initialize counts to zero

        containsWord = new boolean[words.length]; // Array to store word presence
        Arrays.fill(containsWord, false); // Initialize as false
    }

    // Increments count of a specific word in the frequency table
    public void incrementWordCount(int index) {
        this.wordCount[index]++; // Increment word count
    }

    // Method to make a string representation of a Review
    @Override
    public String toString() {
        return "Review{" +
                "review_id='" + review_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", business_id='" + business_id + '\'' +
                ", stars=" + stars +
                ", useful=" + useful +
                ", funny=" + funny +
                ", cool=" + cool +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", business_name='" + business_name + '\'' +
                '}';
    }
}