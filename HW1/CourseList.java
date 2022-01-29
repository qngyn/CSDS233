public class CourseList {
    private final Course[] listOfCourse = new Course[10]; 
    
    //Return the size of the courseList
    public int size(){
        //the command
        System.out.println("Operation: return the size of courseList");
        int count = 0; 
        for (Course course : listOfCourse) {
            if (course != null) {
                count++;
            }
        }
        System.out.println("the size of the current courseList is " + count);
        System.out.println();
        printPreOpCourse();
        printPostOperation();
        return count;
    }   
    
     // Adding course to the courseList 
    public void addCourse(int i, Course course){
        if (i < 0 || i > listOfCourse.length){
           return;
        }
        preOperation("Add course to index", i, course);
      
        if (i < this.sizeForMethod()) {
            for (int j = this.sizeForMethod(); j > i; j--){
                listOfCourse[j] = listOfCourse[j-1];
            }

            listOfCourse[i] = course;
        }
        
        else{
            listOfCourse[this.sizeForMethod()] = course;
        }
        
        printPostOperation();
    }

    public boolean removeCourse(int i){
        if (i < 0 || i >= this.sizeForMethod()) {
            preOpFail("Remove course at this index", i); 
            printPostOperation();
            return false;
        }
       
        else {
            Course course = getIndexOfTheCourse(i);
            preOperation("Remove course at index", i, course);
            
            for (int j = i; j < this.sizeForMethod(); j++){
            listOfCourse[j] = listOfCourse[j + 1];
            }
            
            printPostOperation();
            return true;
        }
    }
    
    public boolean changeCapacity(String courseID, int capacity){
        //the command for the operation
        System.out.println("Operation: Change the capcity of the course has course ID " + courseID + " into " + capacity);
        printPreOpCourse();

        int index = -1; 
        for (int i = 0; i < this.sizeForMethod(); i++) {
            if (listOfCourse[i].getCourseID().equals(courseID)){
                listOfCourse[i].setCapacity(capacity);
                index = i;
            }
        }
      
        if (index != -1) {
            printPostOperation();
            return true;
        }
        
        else {
            System.out.println("Unable to find this course in the list to change the capacity");
            printPostOperation();
            return false;
        }
    }

    //return the course at ith index
    public Course getCourseWithIndex(int i){
        System.out.println("Operation: Return the course at " + i + " index.");
        printPreOpCourse();
        
       if (i < this.sizeForMethod()) {
           System.out.println("The course at index " + i + " is " + listOfCourse[i].toString());
           System.out.println();
           printPostOperation();
           
           return listOfCourse[i];
       } 
       
       else {
           System.out.println("No course at this index");
           System.out.println();
           printPostOperation();
           return null;
       }
    }   
    
    //Return the index by the courseID
    public int SearchCourseID(String courseID){
        System.out.println("Operation: Search the index of the courseID " + courseID);
        int index = -1;
        for (int i = 0; i < this.sizeForMethod(); i++){
            if (listOfCourse[i].getCourseID().equals(courseID)){
                index = i;
            }
        }
      
        printPreOpCourse();
        
        if (index != -1){
            System.out.println("The index of the course " + courseID + " is " + index);
            System.out.println();
            printPostOperation();
            return index;
        }
        
        else {
            System.out.println("No course with this courseID");
            System.out.println();
            printPostOperation();
            return -1;
        }  
    }
    
    public int SearchCourseName(String courseName){
     
        System.out.println("Operation: Search the index of the courseName " + courseName);
        int index = -1;
        for (int i = 0; i < this.sizeForMethod(); i++){
            if (listOfCourse[i].getCourseName().equals(courseName)){
                index = i;
            }
        }

        printPreOpCourse();
        
        if (index != -1){
            System.out.println("The index of the course " + courseName + " is " + index);
            System.out.println();
            printPostOperation();
            return index;
        }
        
        else{
            System.out.println("No course with this courseName");
            System.out.println();
            printPostOperation();
            return -1;
        }
 
    }

    //helper method 

     //Also return the size of CourseList but use inside other method 
        private int sizeForMethod() {
        int count = 0; 
        
        for (Course course : listOfCourse) {
            if (course != null) {
                count++;
            }
        }
        return count;
    }
    
  
    //helper method to get the index of the course
    private Course getIndexOfTheCourse(int i) {
        if (i < this.sizeForMethod()){
            return listOfCourse[i];
        }
        
        return null;
    }
 
    // Printing all the course in the courseList 
    
    private void printCourse(){
        for (int i = 0; i < this.sizeForMethod(); i++){
            System.out.println(i + ". " + listOfCourse[i].toString());
        }
    }
    

     //The method for the failed operation 
    private void preOpFail(String command, int i){
        System.out.println("Operation: " + command + " " + i);
        System.out.println("This index is out of bound ");
        System.out.println();
        printPreOpCourse();
    }
  
    //Method for determining the courseList before the operation 
    private void preOperation(String command, int i, Course course){
        System.out.println("Operation: " + command + " "+i);
        System.out.println("Course: " + course.toString());
        printPreOpCourse();
    }
    
    private void printPreOpCourse(){
        System.out.println("List before the operation");
        printCourse();
        System.out.println();
    }
    
    //Method for printing all the course(s) in the courseList after the operation 
    private void printPostOperation(){
        System.out.println("List after the operation:");
        printCourse();
        System.out.println();
    }    
}
