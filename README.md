Business Recommendation System

About:

This Java application recommends similar businesses based on reviews using TF-IDF calculation. It parses JSON files containing business and review data, calculates review weights, and displays top recommendations to users via a Swing GUI.

Features:

Recommends similar businesses based on reviews
Utilizes TF-IDF algorithm for similarity computation
Parses JSON files containing business and review data
Displays recommendations through a user-friendly GUI interface
Usage:

Run the program.
Select a business from the provided list.
Click on "Find Similar Businesses" to get recommendations.
View recommended businesses in the result area.
Files Included:

BusinessRecommendationSystem.java: Main Java file containing the GUI application and recommendation logic.
Business.java: Class representing a business entity with various attributes.
Review.java: Class encapsulating review data and providing functionality for word frequency and presence.
businessdata.json: JSON file containing business data.
reviewdata.json: JSON file containing review data.
Dependencies:

Gson library for JSON parsing.
Swing for GUI components.
Note:

Ensure that the JSON files (businessdata.json and reviewdata.json) are present in the src/database/ directory before running the application.
