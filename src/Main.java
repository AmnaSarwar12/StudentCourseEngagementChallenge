import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python masterclass");
        Course jmc = new Course("JMC", "Java master class");
//        Student Amna = new Student("PK",2019, 17, "Female", true, pymc,jmc);
//        System.out.println(Amna);
//
//        Amna.watchLecture("jmc",12, 4, 2019);
//        Amna.watchLecture("pymc",14, 5, 2018);
//        System.out.println(Amna);

        Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
                .limit(10)
                .forEach(System.out::println);

        //challenge how many male student and female student are there
        Student[] student = new Student[1000];
        Arrays.setAll(student, (i) -> Student.getRandomStudent(jmc, pymc));

        var maleStudents = Arrays.stream(student)
                .filter(s -> s.getGender().equals('M'));

        System.out.println("# of male student" +maleStudents.count());

        var FemaleStudents = Arrays.stream(student)
                .filter(s-> s.getGender().equals('F'));

        System.out.println("# of female student "+FemaleStudents.count());

        //calculate age < 30, > 30 or < 60 and is  > 60

        List<Predicate<Student>> list = List.of(
                (s) -> s.getAge() < 30,
                (Student s) -> s.getAge() >= 30 && s.getAge() < 60
        );
        long total = 0;
        for(int i = 0; i < list.size(); i++){
            var myStudents = Arrays.stream(student).filter(list.get(i));
            long cnt = myStudents.count();
            total += cnt;
            System.out.printf("# of students (%s) = %d%n", i == 0? " < 30" : ">= 30 & < 60", cnt);
        }
        System.out.println("# of students >= 60 = " + (student.length - total));

        var ageStream = Arrays.stream(student)
                .mapToInt(Student::getAgeEnrolled);
        System.out.println("Stats for student age enrollement" +ageStream.summaryStatistics());

        var currentageStream = Arrays.stream(student)
                .mapToInt(Student::getAge);
        System.out.println("Stats for student age " +currentageStream.summaryStatistics());

        Arrays.stream(student)
                .map(Student::getCountryCode)
                .distinct()
                .sorted()
                .forEach(s->System.out.print(s + " "));

        System.out.println();
        boolean longTerm = Arrays.stream(student)
                .anyMatch(s -> (s.getAge() - s.getAgeEnrolled() > 7) &&
                        (s.getMonthSinceActive() < 12));
        System.out.println("Long term Student? " +longTerm);

        long longTermCount = Arrays.stream(student)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() > 7) &&
                        (s.getMonthSinceActive() < 12))
                .count();
        System.out.println("Long term Student? " +longTermCount);

         Arrays.stream(student)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() > 7) &&
                        (s.getMonthSinceActive() < 12))
                 .filter(s-> !s.HasProgrammingExperience())
                 .limit(5)
                .forEach(System.out::println);




    }
}