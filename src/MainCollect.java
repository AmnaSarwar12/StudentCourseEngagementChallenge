import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainCollect {

    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python masterclass");
        Course jmc = new Course("JMC", "Java master class");

        List<Student> students = Stream.generate(
                ()-> Student.getRandomStudent(jmc, pymc))
                .limit(1000)
                .toList();

        Set<Student> AustralianStudent = students.stream()
                .filter((s) -> s.getCountryCode().equals("AU"))
                .collect(Collectors.toSet());

        System.out.println("# of australian student = " +AustralianStudent.size());

        Set<Student> AgeStudent = students.stream()
                .filter((s) -> s.getAgeEnrolled() < 30)
                .collect(Collectors.toSet());
        System.out.println("Students Age under thirty = " +AgeStudent.size());

        Set<Student> YoungAussie2 = students.stream()
                .filter((s) -> s.getAgeEnrolled() > 30)
                .filter((s) -> s.getCountryCode().equals("AU"))
                .collect(() -> new TreeSet<>(Comparator.comparing(Student::getStudentId)), TreeSet::add, TreeSet::addAll);
        YoungAussie2.forEach((s) -> System.out.print(s.getStudentId() + " "));
        System.out.println();

        String CountryList = students.stream()
                .map(Student::getCountryCode)
                .distinct()
                .sorted()
                .reduce("", (r, v) -> r + " "+ v);
        System.out.println("countryList = " +CountryList);
    }
}
