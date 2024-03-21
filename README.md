**Business Recommendation System**

**Overview:**

This Java application offers business recommendations by analyzing review data. It calculates review weights using the TF-IDF algorithm and presents top suggestions through a user-friendly GUI.

**Features:**
- Recommends similar businesses based on reviews
- Utilizes TF-IDF algorithm for similarity computation
- Parses JSON files for business and review data
- Provides recommendations through a Swing GUI interface

**Usage:**

1. Run the program.
2. Select a business from the provided list.
3. Click on "Find Similar Businesses" to get recommendations.
4. View recommended businesses in the result area.

**Included Files:**

- `BusinessRecommendationSystem.java`: Main application file with GUI and logic.
- `Business.java`: Class representing business entities.
- `Review.java`: Class for review data encapsulation.
- `businessdata.json`: JSON file with business data.
- `reviewdata.json`: JSON file with review data.

**Dependencies:**

- Gson library for JSON parsing.
- Swing for GUI components.

**Note:**

Ensure that the JSON files (`businessdata.json` and `reviewdata.json`) are present in the `src/database/` directory before running the application.
