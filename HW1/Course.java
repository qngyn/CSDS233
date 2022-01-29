public class Course {
    private String courseID;
    private String courseName;
    private int capacity; 
    
    
    /**
     * Constructor for the Course class
     */
    public Course(String courseID, String courseName, int capacity){
        if (courseID == null || courseID.isEmpty()||
                courseName == null || courseName.isEmpty()||
                capacity < 0) {
            throw new IllegalArgumentException("the input is not valid. Please try again!");
        }
       
        this.courseID = courseID;
        this.courseName = courseName;
        this.capacity = capacity;     
    }
    
    /**
     * Get the courseID
     * @return the courseID
     */
    public String getCourseID(){
        return this.courseID;
    }
    
    /**
     * Set the courseID
     * @param newID set the new ID for the courseID
     */
    public void setCourseID(String newID){
        this.courseID = newID;
    }
    
    /**
     * Get the courseName
     * @return courseName
     */
    public String getCourseName(){
        return this.courseName;
    }
    
    /**
     * Set the new name for the course
     * @param newName the new Name for the course
     */
    public void setCourseName(String newName){
        this.courseName = newName;
    }
    
    /**
     * Get the capacity of the course
     * @return the capacity of the course
     */
    public int getCapacity(){
        return this.capacity;
    }
    
    /**
     * Set the new capacity
     * @param c new capacity for the course
     */
    public void setCapacity(int c){
        this.capacity = c; 
    }
    
    public String toString(){
        return ("courseID: " + courseID + ", courseName: " + courseName + ", capacity: " + capacity);
    }
    
}
