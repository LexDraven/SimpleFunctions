package Eight;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lambdas {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.forEach(System.out::print);
        System.out.println(sumAll(numbers, n -> true));
        System.out.println(sumAll(numbers, n -> n % 2 == 0));
        System.out.println(sumAll(numbers, n -> n > 3));
    }

    public static int sumAll(List<Integer> numbers, Predicate<Integer> p) {
        int total = 0;
        for (int number : numbers) {
            if (p.test(number)) {
                total += number;
            }
        }
        return total;
    }

    private void checkIt(WebDriver driver){
        List<String> links = driver.findElements(By.tagName("a")).stream().map(n->n.getAttribute("href")).collect(Collectors.toList());
        links = links.stream().filter(n->n!=null).collect(Collectors.toList());
        links.parallelStream().filter(n -> !isLinkAlive(n)).forEach(System.out::println);
    }

    public boolean isLinkAlive(String url){
        return true;
    }
}
