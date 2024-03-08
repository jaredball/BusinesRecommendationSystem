
// Business class representing a business entity with various attributes such as ID, name, address, and categories.

import java.util.Map;

public class Business {

    // Business attributes
    private String business_id; // Unique identifier for the business
    private String name; // Name of the business
    private String address; // Address of the business
    private String city; // City where the business is located
    private String state; // State where the business is located
    private String postal_code; // Postal code of the business location
    private double latitude; // Latitude coordinate of the business location
    private double longitude; // Longitude coordinate of the business location
    private double stars; // Average rating of the business
    private int review_count; // Number of reviews for the business
    private int is_open; // Indicator if the business is open (0 for closed, a for open)
    private Map<String, String> attributes; // Key-value pairs for various attributes of the business
    private String categories; // Category the business falls under
    private Map<String, String> hours; // Business hours

    // Getters for Business attributes
    public String getBusiness_id() {
        return business_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getStars() {
        return stars;
    }

    public int getReview_count() {
        return review_count;
    }

    public int getIs_open() {
        return is_open;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getCategories() {
        return categories;
    }

    public Map<String, String> getHours() {
        return hours;
    }

    // Method to make a string representation of the Business
    @Override
    public String toString() {
        return "Business{" +
                "business_id='" + business_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stars=" + stars +
                ", review_count=" + review_count +
                ", is_open=" + is_open +
                ", attributes=" + attributes +
                ", categories='" + categories + '\'' +
                ", hours=" + hours +
                '}';
    }
}