package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentTest {
    Student validStudent;
    @BeforeEach
    void setup(){
        validStudent = new Student(
                1, "Ram Thapa", Gender.MALE,
                "9841000000", "ram@example.com",
                "Kathmandu", "Hari Sharma",
                LocalDate.of(2000, 1, 15)
        );
    }
    //Accepting the name
    @Test
    void shouldReturnCorrectfullName(){
        assertEquals("Ram Thapa", validStudent.getFullName());
    }
    @Test
    void shouldTrimExtraSpacesFromName(){
        Student s = new Student(
                1,
                "  Ram   Sharma  ",   // ← messy input
                Gender.MALE,
                "9841000000",
                "ram@example.com",
                "Kathmandu",
                "Hari Sharma",
                LocalDate.of(2000, 1, 15)
        );
        String actualName = s.getFullName();
        assertEquals("Ram Sharma", actualName);
    }
    @Test
    void shouldThrowWhenNameIsShorterThanTwoCharacters() {
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                    1,
                    "R",
                    Gender.MALE,
                    "9841000000",
                    "ram@example.com",
                    "Kathmandu",
                    "Hari Sharma",
                    LocalDate.of(2000, 1, 15)
            );
        });
    }
    @Test
    void shouldThrowWhenNameContainsNumbers() {
        assertThrows(IllegalArgumentException.class, ()->{
            new Student(
                1,
                "Ram123",
                Gender.MALE,
                "9841000000",
                "ram@example.com",
                "Kathmandu",
                "Hari Sharma",
                LocalDate.of(2000, 1, 15)
            );
        });

    }

    @Test
    void shouldThrowWhenNameIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                    1,
                    null,
                    Gender.FEMALE,
                    "9841787676",
                    "erish@example.com",
                    "bhaktapur",
                    "Hari thapa",
                    LocalDate.of(2001, 1, 3)
            );
        });
    }
    //Location testing
    @Test
    void shouldGiveLocation(){

        assertEquals("Kathmandu", validStudent.getLocation());
    }
    @Test
    void shouldThrowWhenLocationIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                    1,
                    "Bikash",
                    Gender.FEMALE,
                    "9841787676",
                    "erish@example.com",
                    null,
                    "Hari thapa",
                    LocalDate.of(2001, 1, 3)
            );
        });
    }
    @Test
    void shouldThrowWhenLocationIsShorterThanTwoCharacters(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                    1,
                    "Bikash",
                    Gender.FEMALE,
                    "9841787676",
                    "erish@example.com",
                    "i",
                    "Hari thapa",
                    LocalDate.of(2001, 1, 3)
            );
        });
    }
    //Email Testing
    @Test
    void shouldReturnEmail(){
        assertEquals("ram@example.com", validStudent.getEmail());
    }
    @Test
    void shouldThrowWhenEmailIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                    1,
                    "Bikash",
                    Gender.FEMALE,
                    "9841787676",
                    null,
                    "bhaktapur",
                    "Hari thapa",
                    LocalDate.of(2001, 1, 3)
            );
        });
    }

    @Test
    void shouldThrowWhenEmailHaveExtraSpecialLetters(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Student(
                    1,
                    "Bikash",
                    Gender.FEMALE,
                    "9841787676",
                    "bbik@example..com",
                    "bhaktapur",
                    "Hari thapa",
                    LocalDate.of(2001, 1, 3)
            );
        });
    }

    //Guardian Name Testing
    @Test
    void shouldReturnGuardianName(){
        assertEquals("Hari Sharma", validStudent.getGuardianName());
    }
    @Test
    void shouldThrowWhenGuardianNameIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
           new Student(
                   1,
                   "Bikash",
                   Gender.FEMALE,
                   "9841787676",
                   "bbi@example.com",
                   "bhaktapur",
                   null,
                   LocalDate.of(2001, 1, 3)
           );
        });
    }
    //Phone Testing
    @Test
    void shouldReturnPhoneNumber(){
        assertEquals("9841000000", validStudent.getPhoneNumber());
    }
    @Test
    void shouldThrowNullWhenPhoneNumberIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                1,
                "Bikash",
                Gender.FEMALE,
                null,
                "bbi@example.com",
                "bhaktapur",
                "Akrish thapa",
                LocalDate.of(2001, 1, 3)
            );
        });
    }
    //Date of Birth Testing
    @Test
    void shouldReturnDateOfBirth(){
        assertEquals(LocalDate.of(2000,1,15), validStudent.getDateOfBirth());
    }
    @Test
    void shouldReturnWhenDateOfBirthIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Student(
                    1,
                    "Bikash",
                    Gender.FEMALE,
                    "9878788776",
                    "bbi@example.com",
                    "bhaktapur",
                    "Akrish thapa",
                    null
            );
        });
    }
    @Test
    void shouldThrowWhenDateOfBirthIsBefore1900() {
        assertThrows(IllegalArgumentException.class, ()->{
           Student s = new Student(
                   1,
                   "Bikash",
                   Gender.FEMALE,
                   "9878788776",
                   "bbi@example.com",
                   "bhaktapur",
                   "Akrish thapa",
                   LocalDate.of(1889, 12, 31)
           );
        });
    }
    @Test
    void shouldThrowWhenStudentIsYoungerThanThreeYears() {
        assertThrows(IllegalArgumentException.class, ()->{
            new Student(
                    1,
                    "Bikash",
                    Gender.FEMALE,
                    "9878788776",
                    "bbi@example.com",
                    "bhaktapur",
                    "Akrish thapa",
                    LocalDate.now().minusYears(2)
            );
        });
    }
    }
