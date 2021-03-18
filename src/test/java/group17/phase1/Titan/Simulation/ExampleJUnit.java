package group17.phase1.Titan.Simulation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//idea : IntellijUltra -> Settings->Editor->Inlay Hints
// or right-click (disable/ settings)

// can be run from whatever idea using gradle
class ExampleJUnit
{

    @Test
    void demoTest() {
        assertEquals(1,1);
    }

    @Test
    @Disabled("Not implemented yet")
    void demoWrong(){
        assertEquals(2,1);
    }

    @Test
    @DisplayName("This test has a particular descriptive name  ' $ %")
    void displayName() {
        assertEquals(1,1);
    }

    @Test
    @Disabled("Not implemented yet")
    void failMethod(){
        fail("Not implemented yet");
    }


    @Test
    @DisplayName("Should check all the items in the list")
    void multipleAssertions(){
        var integers = List.of(2, 3, 5, 7);

        //imagine one of them is wrong
        assertEquals(2,integers.get(0));
        assertEquals(3,integers.get(1));
        assertEquals(5,integers.get(2));
        assertEquals(7,integers.get(3));
        //if one assertion fails the next ones are not going to be executed anyway
    }

    @Test
    @DisplayName("Should check all the items in the list")
    void groupAssertions(){
        var integers = List.of(2, 3, 5, 7);
        Assertions.assertAll(
                () -> assertEquals(2, integers.get(0)),
                () -> assertEquals(3, integers.get(1)),
                () -> assertEquals(5, integers.get(2)),
                () -> assertEquals(7, integers.get(3))  );

        //like this all of them get anyway executed
    }

    boolean field = true;
    @Test
    @DisplayName("Should only run the test if some criteria")
    void ShouldOnlyRunTheTestIfSomeCriteria() throws NoSuchFieldException {
        Assumptions.assumeFalse(field);
        // this test runs only if assumption passes
        assertEquals(1,1);
    }

    @ParameterizedTest(name = "testing number {0}")
    @DisplayName("Should sum 10 times different numbers ")
    @ValueSource(ints = {3,4,5,8,14})
    void shouldSum10WithDifferentNumbers(int expectedNumber) {
        int x = 10, y = x;
        for (int i =1; i< expectedNumber; i++)
            x+=10;
        assertEquals(x,y*expectedNumber);

        //this will run once for each parameter
    }

    @ParameterizedTest(name = "testing number {0}")
    @DisplayName("Should sum 10 times different numbers if accepted ")
    @ValueSource(ints = {1001, Integer.MAX_VALUE})
    void shouldSum10WithDifferentNumbersButNotSome(int expectedNumber) {
        assertThrows(IllegalArgumentException.class,
                () -> sum10(expectedNumber));

    }

    int sum10 (int expectedNumber){
        if (expectedNumber>1000)
            throw new IllegalArgumentException("invalid input");

        int x = 10;
        for (int i =1; i< expectedNumber; i++)
            x+=10;
        return x;
    }


    private static class Shape
    {
        private final int sum = 1000;
        private final String shape = "square";
    }
    //GROUPING TESTS
    @Nested
    class NestedClass
    {

        Shape s = new Shape();
        @Nested
        class Allowed{
            @Test
            @DisplayName("Look at the sum")
            void LookAtTheSum() {
                assertEquals(1000, new Shape().sum);
            }
            @Test
            @DisplayName("Look at the name")
            void LookAtTheName() {
                assertEquals("square", new Shape().shape);
            }
        }

        @Nested
        class NotAllowed
        {
            @Test
            @DisplayName("Look at the sum")
            void LookAtTheSum() {
                assertNotEquals(s, new Shape());
            }
        }
    }

}
