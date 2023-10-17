import java.security.Key;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Student {
    private static long Laststudentid = 1;
    private final static Random random =  new Random();

    private final long studentId;
    private final String countryCode;
    private final int yearEnrolled;

    private final int ageEnrolled;

    private final String gender;
    private final boolean ProgrammingExperience;

    private final Map<String, CourseEngagemenr> engagementMap = new HashMap<>();

    public Student( String countryCode, int yearEnrolled,int ageEnrolled, String gender, boolean programmingExperience, Course... courses) {
        this.studentId =  Laststudentid;
        this.countryCode = countryCode;
        this.yearEnrolled = yearEnrolled;
        this.ageEnrolled = ageEnrolled;
        this.gender = gender;
        ProgrammingExperience = programmingExperience;

        for(Course course : courses){
            addCourse(course, LocalDate.of(yearEnrolled, 1, 1));
        }
    }

    public void addCourse(Course newCourse){
        addCourse(newCourse, LocalDate.now());
    }

    public void addCourse(Course newCourse, LocalDate enrollDate){
        engagementMap.put(newCourse.CourseCode(), new CourseEngagemenr(newCourse, enrollDate, "Enrollement"));
    }

    public long getStudentId() {
        return studentId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getYearEnrolled() {
        return yearEnrolled;
    }

    public String getGender() {
        return gender;
    }

    public boolean HasProgrammingExperience() {
        return ProgrammingExperience;
    }

    public Map<String, CourseEngagemenr> getEngagementMap() {
        return Map.copyOf(engagementMap);
    }

    public int getAgeEnrolled() {
        return ageEnrolled;
    }

    public int getYearSinceEnrolled(){
        return LocalDate.now().getYear() - yearEnrolled;
    }

    public int getAge(){
        return ageEnrolled + getYearSinceEnrolled();
    }

    public int getMonthSinceActive(String courseCode){
        CourseEngagemenr infor = engagementMap.get(courseCode);
        return infor == null ? 0 : infor.getMonthSinceActive();
    }

    public int getMonthSinceActive(){
        int inActiveMonths = (LocalDate.now().getYear() - 2014) * 12;
        for(String key : engagementMap.keySet()){
            inActiveMonths = Math.min(inActiveMonths, getMonthSinceActive(key));
        }
        return inActiveMonths;
    }

    public double getPercentComplete(Course CourseCode){
        var info = engagementMap.get(CourseCode);
        return (info == null) ? 0: info.getPercentComplete();
    }

    public void watchLecture(String CourseCode, int LectureNumber, int Month, int year){
        var activity = engagementMap.get(CourseCode);
        if(activity != null){
            activity.watchLecture(LectureNumber, LocalDate.of(year,Month, 1));
        }
    }

    private static String getRandomVal(String... data){
        return data[random.nextInt(data.length)];
    }

    public static Student getRandomStudent(Course... course){
        int maxYear = LocalDate.now().getYear() + 1;
        Student student =  new Student(
                getRandomVal("AU","CA","PG","UP","PK","GH"),
                random.nextInt(2015, maxYear),
                random.nextInt(18, 90),
                getRandomVal("M","P","A"),
                random.nextBoolean(),
                course);

        for(Course c: course){
            int lecture = random.nextInt(1, c.LectureCount());
            int year = random.nextInt(student.getYearEnrolled(), maxYear);
            int month = random.nextInt(1, 13);
            if(year == (maxYear - 1)){
                if(month > LocalDate.now().getMonthValue()){
                    month = LocalDate.now().getMonthValue();
                }
            }
            student.watchLecture(c.CourseCode(), lecture, month, year);
        }
        return student;


    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", countryCode='" + countryCode + '\'' +
                ", yearEnrolled=" + yearEnrolled +
                ", gender='" + gender + '\'' +
                ", ProgrammingExperience=" + ProgrammingExperience +
                ", engagementMap=" + engagementMap +
                '}';
    }
}
