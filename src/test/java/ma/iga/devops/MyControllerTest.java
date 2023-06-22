package ma.iga.devops;

import ma.iga.devops.controller.MyController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyControllerTest {

    @DisplayName("Test Add")
    @Test
    public void testAdd() {
        // Arrange
        MyController myController = new MyController();
        Integer n1 = 2;
        Integer n2 = 3;
        Integer expectedSum = 5;

        // Act
        Integer actualSum = myController.add(n1, n2);

        // Assert
        Assertions.assertEquals(expectedSum, actualSum, "Sum is incorrect");
    }
}