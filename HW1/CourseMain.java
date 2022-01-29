public class CourseMain {


    /**
     * @param args the command line arguments
     * Demo for the Course and CourseList 
     */
    public static void main(String[] args) {
        
        //Creating course 
        Course course1 = new Course("234", "Data Structures", 50);
        Course course2 = new Course("132", "Intro to Python", 200);
        Course course3 = new Course("425", "Comp Net", 50);
        Course course4 = new Course("281", "Intro to Logic Des", 40);
        
        //Demo Course 
        System.out.println("Demo method from Course class");
        //Demo get/set Course ID
        System.out.println("Get/Set courseID");
        System.out.println("Before set new ID: " + course1.getCourseID());
        course1.setCourseID("233");
        System.out.println("After set new ID: " + course1.getCourseID());
        System.out.println();
        
        //Demo get/set courseName
        System.out.println("Get/Set courseName");
        System.out.println("Before set new name: " + course2.getCourseName());
        course2.setCourseName("Intro to Java");
        System.out.println("After set new name: " +course2.getCourseName());
        System.out.println();
        
        //Demo get/set capacity
        System.out.println("Get/Set capacity");
        System.out.println("Before set new name: " + course3.getCapacity());
        course3.setCapacity(150);
        System.out.println("After set new name: " +course3.getCapacity());
        System.out.println();
        
        
        //Demo CourseList
        //creating a courseList
        CourseList newList = new CourseList();
        
        //test size
        System.out.println("Demo size() method");
        newList.size();
        
        //Demo addCourse
        System.out.println("Demo addCourse() method");
        newList.addCourse(9, course1);
        newList.addCourse(0, course2);
        newList.addCourse(5, course3);
        newList.addCourse(2, course4);
        
        newList.size();

        //Demo removeCourse
        System.out.println("Demo removeCourse method");
        newList.removeCourse(0); 
        newList.removeCourse(5);
        
        //test size after remove
        System.out.println("Demo size after remove");
        newList.size();
  
        //Demo changeCapacity method
        newList.changeCapacity("233", 150);
        newList.changeCapacity("307", 300);
        
        //Demo getCourseWithIndex
        System.out.print("Demo getCourseWithIndex");
        newList.getCourseWithIndex(0);
        newList.getCourseWithIndex(1);
        newList.getCourseWithIndex(8);
        
        //Demo SearchCourseID
        System.out.println("Demo SearchCourseID");
        newList.SearchCourseID("281");
        newList.SearchCourseID("132");

          //Demo SearchCourseName
          System.out.println("Demo SearchCourseName");
          newList.SearchCourseName("Intro to Biochemistry");
          newList.SearchCourseName("Comp Net");
          newList.SearchCourseName("Intro to Logic Des");
    }
}
