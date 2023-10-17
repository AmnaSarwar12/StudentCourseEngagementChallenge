public record Course(String CourseCode, String title, int LectureCount) {

    public Course {
        if(LectureCount <= 0){
            LectureCount = 1;
        }
    }
    public Course(String courseCode, String title) {
        this(courseCode, title, 40);


    }

    @Override
    public String toString() {
        return "%s %s".formatted(CourseCode, title);
    }
}
