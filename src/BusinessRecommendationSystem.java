
// This Java Swing application recommends similar businesses based on reviews using TF-IDF calculation. It parses JSON
// files containing business and review data, calculates review weights, and displays top recommendations to users.
// Utilizes Gson for JSON parsing, Swing for GUI components, and implements TF-IDF algorithm for similarity computation.

import com.google.gson.Gson; // Importing Gson library for JSON parsing
import java.io.FileReader;
import java.io.BufferedReader; // For reading text efficiently
import java.io.IOException;
import java.util.Arrays; // Importing utility classes
import java.util.Comparator;
import java.util.Hashtable;
import javax.swing.*; // Importing Swing GUI components
import java.awt.BorderLayout;
import java.awt.Dimension;

// Main class representing the Business Recommendation System
public class BusinessRecommendationSystem extends JFrame {

    // GUI components declaration
    private JComboBox<String> businessSelector;
    private JButton searchButton;
    private JTextArea resultArea;

    // Constructor to initialize the GUI
    public BusinessRecommendationSystem() {
        // Setting up the JFrame
        setTitle("Business Recommendation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(625, 400);
        setLocationRelativeTo(null);

        // Creating GUI components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel resultPanel = new JPanel(new BorderLayout());

        // Initializing GUI components
        businessSelector = new JComboBox<>();
        businessSelector.addItem("Select a business from the list...");
        searchButton = new JButton("Find Similar Businesses");
        resultArea = new JTextArea(15, 40);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Adding components to panels
        inputPanel.add(new JLabel("Select a business:"));
        inputPanel.add(businessSelector);
        buttonPanel.add(searchButton);
        resultPanel.add(new JLabel("Similar Businesses:"), BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        resultPanel.setPreferredSize(new Dimension(50, 200));

        // Adding panels to mainPanel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Adding action listener to search button
        searchButton.addActionListener(e -> performSearch());

        // Loading businesses from JSON file
        loadBusinesses();
    }

    // Main method to start the application
    public static void main(String[] args) {
        // Running the GUI application on the event dispatch thread
        SwingUtilities.invokeLater(() -> new BusinessRecommendationSystem().setVisible(true));
    }

    // Method to load businesses from JSON file
    private void loadBusinesses() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/database/businessdata.json"))) {
            Gson gson = new Gson();
            String line;
            while ((line = reader.readLine()) != null) {
                // Parsing each line of JSON data to a Business object and adding it to the combo box
                Business business = gson.fromJson(line, Business.class);
                businessSelector.addItem(business.getName());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an error occurs during file reading
        }
    }

    // Method to perform search for similar businesses
    private void performSearch() {
        // Clearing the result area
        resultArea.setText("");

        // Retrieving the selected business from the gui box
        String selectedBusinessName = (String) businessSelector.getSelectedItem();

        // Checking if a business is selected
        if (selectedBusinessName.equals("Select a business from the list...")) {
            // Displaying an error message if no business is selected
            JOptionPane.showMessageDialog(this, "Please select a business!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Reading business data from JSON file and storing in a Hashtable for quick retrieval
        try (BufferedReader br = new BufferedReader(new FileReader("src/database/businessdata.json"))) {
            Gson gson = new Gson();
            String line;
            Hashtable<String, Business> businessHashtable = new Hashtable<>();
            while ((line = br.readLine()) != null) {
                // Parsing business data and storing it in the Hashtable
                Business firstBusiness = gson.fromJson(line, Business.class);
                businessHashtable.put(firstBusiness.getBusiness_id(), firstBusiness);
            }

            // Processing review data
            BufferedReader brReview = new BufferedReader(new FileReader("src/database/reviewdata.json"));
            Gson gsonReview = new Gson();

            // Variables for review processing
            int reviewCount = 0;
            int maxReviewsToParse = 10000;
            Review[] reviewList = new Review[maxReviewsToParse];

            // Reading reviews and preparing them for analysis
            while ((line = brReview.readLine()) != null && reviewCount < maxReviewsToParse) {
                // Parsing review data and setting business names for each review
                Review currentReview = gsonReview.fromJson(line, Review.class);
                currentReview.setBusiness_name(businessHashtable.get(currentReview.getBusiness_id()).getName());
                reviewList[reviewCount] = currentReview;
                reviewCount++;
            }

            // Filtering out the selected business and preparing review data for analysis
            String selectedBusinessReview = findReviewForBusiness(reviewList, selectedBusinessName);
            reviewList = Arrays.stream(reviewList)
                    .filter(s -> !(s.getBusiness_name().equalsIgnoreCase(selectedBusinessName)))
                    .toArray(Review[]::new);
            String[] inputSplit = cleanAndSplitString(selectedBusinessReview);

            // Initializing frequency table for each review and preparing data for TF-IDF calculation
            int[] termDocumentFrequency = new int[inputSplit.length];
            for (Review review : reviewList) {
                review.initializeFrequencyTable(inputSplit);
                String[] cleanReviewData = cleanAndSplitString(review.getReview_text());
                for (String i : cleanReviewData) {
                    for (int j = 0; j < inputSplit.length; j++) {
                        if (inputSplit[j].equalsIgnoreCase(i)) {
                            review.incrementWordCount(j);
                            review.setContainsWordTrue(j);
                        }
                    }
                }
                // Counting document frequency for each term in the selected review set
                for (int i = 0; i < review.getContainsWord().length; i++) {
                    if (review.getContainsWord()[i]) {
                        termDocumentFrequency[i]++;
                    }
                }
            }

            // Calculating total weight of each review and sorting them by weight
            for (Review r : reviewList) {
                r.setTotalWeight(calculateReviewWeight(r.getWordCount(), termDocumentFrequency, maxReviewsToParse));
            }
            Arrays.sort(reviewList, Comparator.comparingDouble(Review::getTotalWeight).reversed());

            // Displaying the recommended businesses
            int recommendedBusinessCount = 2;
            for (int i = 0; i < recommendedBusinessCount; i++) {
                Business recommendedBusiness = businessHashtable.get(reviewList[i].getBusiness_id());
                resultArea.append(recommendedBusiness.getName() + "\n");
            }
        } catch (IOException e) {
            // Displaying an error message if an error occurs during file reading
            JOptionPane.showMessageDialog(this, "An error occurred while searching.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to calculate weight of a review
    private static double calculateReviewWeight(int[] tfData, int[] dfData, int totalReview) {
        double total = 0;
        for (int i = 0; i < tfData.length; i++) {
            // Calculating weight using TF-IDF formula
            total += Math.log10(1 + tfData[i]) * ((double) totalReview / (dfData[i] + 1));
        }
        return total;
    }

    // Method to clean string from special characters and split into words
    private static String[] cleanAndSplitString(String rawString) {
        rawString = rawString.replaceAll("[^a-zA-Z']", " "); // Removing non-alphabetic characters except apostrophes
        rawString = rawString.toLowerCase(); // Converting text to lowercase
        // Splitting text into words and filtering out empty strings
        return Arrays.stream(rawString.split("\\s+")).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }

    // Method to search for reviews of a given business
    private static String findReviewForBusiness(Review[] reviewList, String userInput) {
        for (Review r : reviewList) {
            if (r.getBusiness_name().equalsIgnoreCase(userInput)) {
                // Returning the review text if the business name matches the user input
                return r.getReview_text();
            }
        }
        return null; // Returning null if no review is found for the given business
    }
}
