import java.time.LocalDate;
import java.time.Period;

public class CourseEngagemenr {

    private final Course  course;

    private final LocalDate enrollementDate;
    private String engagementType;
    private int lastLecture;
    private LocalDate lastActivityDate;

    public CourseEngagemenr(Course course, LocalDate enrollementDate, String engagementType) {
        this.course = course;
        this.enrollementDate = this.lastActivityDate = enrollementDate;
        this.engagementType = engagementType;
    }

    public String getCourseCode() {
        return course.CourseCode();
    }

    public int getEnrollementDate() {
        return enrollementDate.getYear();
    }

    public String getEngagementType() {
        return engagementType;
    }

    public int getLastLecture() {
        return lastLecture;
    }

    public int getLastActivityYear() {
        return lastActivityDate.getYear();
    }

    public String getLastActivityMonth(){
        return "%tb".formatted(lastActivityDate);
    }

    public double getPercentComplete(){
        return lastLecture * 100.0 / course.LectureCount();
    }

    public int getMonthSinceActive(){
        LocalDate now = LocalDate.now();
        var months = Period.between(lastActivityDate, now).toTotalMonths();
        return (int) months;
    }

    public void watchLecture(int lecture, LocalDate date){
        lastLecture= Math.max(lecture, lastLecture);
        lastActivityDate = date;
        engagementType = "Lecture " + lastLecture;
    }

    @Override
    public String toString() {
        return "%s: %s %d %s [%d]".formatted(course.CourseCode(), getLastActivityMonth(), getLastActivityYear(), engagementType, getMonthSinceActive());
    }
}
