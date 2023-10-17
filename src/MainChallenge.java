import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class MainChallenge {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python masterclass", 100);
        Course jmc = new Course("JMC", "Java master class", 50);
        Course javagames = new Course("javagames","Creating Java games");

//        Stream.generate(() -> Student.getRandomStudent(jmc, pymc, javagames))
//                .limit(5000)
//                .forEach(System.out::println);

        List<Student> students = Stream.generate(
                        ()-> Student.getRandomStudent(jmc, pymc, javagames))
                .limit(5000)
                .toList();

        double totalPercent =students.stream()
                .mapToDouble(s->s.getPercentComplete("JMC"))
                .reduce(0, Double::sum);

        double avePercent = totalPercent / students.size();
        System.out.printf("Average Percent Complete = %f%% %n", avePercent);

        int topPercent  = (int)(1.25 * avePercent);
        System.out.printf("Best Percen Complete = %d%% %n", topPercent);

        Comparator<Student> longTermStudent = Comparator.comparing(Student::getYearEnrolled);

        List<Student> hardworker = students.stream()
                .filter(s-> s.getMonthSinceActive("JMC") == 0)
                .filter(s-> s.getPercentComplete("JMC")>=topPercent)
                .sorted(longTermStudent)
                .toList();
        System.out.println("Hardworker = " +hardworker);

        hardworker.forEach(s -> {
            s.addCourse(javagames);
            System.out.println(s);
        });



    }
}
