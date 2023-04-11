import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;


public class Test1 {
    public static void main(String[] args) {
        System.out.println(RandomStringUtils.randomAlphanumeric(8));
        System.out.println(RandomStringUtils.random(8));
        System.out.println(RandomStringUtils.randomAscii(8));
        System.out.println(RandomStringUtils.randomNumeric(8));
        System.out.println(RandomStringUtils.randomAlphabetic(8));


    }

    public void test1(){

    }
}
