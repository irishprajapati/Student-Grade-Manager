package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeacherTest {
    Teacher validTeacher;
    @BeforeEach
    void Setup(){
        validTeacher = new Teacher(
                "Shyam Thapa",
                "Bhaktapur",
                "9841787667"
        );
    }
    //Testing name
    @Test
    void shouldReturnFullName(){
        assertEquals("Shyam Thapa", validTeacher.getFullName());
    }
    @Test
    void shouldReturnWhenFullNameIsNull(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Teacher(
                    null,
                    "bhaktapur",
                    "9841655645"
            );
        });
    }
    @Test
    void shouldReturnIfFullNameIsLessThanFiveCharacter(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Teacher(
                    "ii",
                    "Bhaktapur",
                    "9841987867"
            );
        });
    }
    @Test
    void shouldTrimFullName(){
        Teacher t = new Teacher(
                " Ram   Thapa",
                "Bhaktapur",
                "9841676554"
        );
        String teacherName = t.getFullName();
        assertEquals("Ram Thapa", teacherName);
    }
    //Testing Location
    @Test
    void ShouldReturnLocation(){
        Teacher t = new Teacher(
                "Krish thapa",
                "bhaisipati",
                "9841787656"
        );
        String teacherLocation = t.getLocation();
        assertEquals("bhaisipati", teacherLocation);
    }
    @Test
    void shouldTrimExtraSpaces(){
        Teacher t = new Teacher(
                "Krish thapa",
                "    bhaisipati    ",
                "9841787656"
        );
        String getLocation = t.getLocation();
        assertEquals("bhaisipati", getLocation);
    }
    @Test
    void shouldThrowNullIfLocationIsNull(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Teacher(
                    "Teacher",
                    null,
                    "9841787656"
            );
        });

    }
    @Test
    void shouldThrowIfLocationIsLessThanFourCharacters(){
        assertThrows(IllegalArgumentException.class, ()->{
           new Teacher(
                   "Teacher",
                   "iii",
                   "9841787656"
           );
        });
    }
    //Testing phoneNumber
    @Test
    void shouldReturnPhoneNumber(){
        assertEquals("9841787667", validTeacher.getPhoneNumber());
    }
    @Test
    void shouldThrowNullWhenPhoneNumberIsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Teacher(
                    "Bikash",
                    "bhaktapur",
                    null
            );
        });
    }
}
