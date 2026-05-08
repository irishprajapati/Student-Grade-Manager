package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubjectTest {
    Subject validSubject;
    @BeforeEach
    void Setup(){
        validSubject = new Subject(
                "history",
                1
        );
    }
    //subject name testing
    @Test
    void shouldReturnSubjectName(){
        assertEquals("history", validSubject.getName());
    }

    @Test
    void shouldThrowIfSubjectNameIsNull(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Subject(
                    null,
                    2
            );
        });
    }
    @Test
    void shouldTrimAllSpaces(){
        Subject s = new Subject(
                "   Nepali  ",
                1
        );
        String getSubjectName = s.getName();
        assertEquals("Nepali", getSubjectName);
    }
    @Test
    void shouldThrowIfSubjectNameIsLessThanThreeCharacters(){
        assertThrows(IllegalArgumentException.class, ()->{
           new Subject(
                   "ii",
                   1
           );
        });
    }

}
