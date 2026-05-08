package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    User validUser;
    @BeforeEach
    void Setup(){
        validUser = new User(
                "Bikash Kayastha",
                "Bikashbikash_123$",
                Role.TEACHER
        );
    }
    //Accepting the name
    @Test
    void shouldReturnFullName(){
        assertEquals("Bikash Kayastha", validUser.getfullname());
    }
    @Test
    void shouldThrowIfFullNameIsNull(){
        assertThrows(IllegalArgumentException.class, () -> {
           new User(
                   null,
                   "Bikashbqb_123$",
                   Role.TEACHER
           );
        });
    }
    @Test
    void shouldRemoveExtraSpaces(){
       User user = new User(
               "   Rameshwor     ",
               "Ramchor_1233$",
               Role.STUDENT
       );
       String getActualName = user.getfullname();
       assertEquals("Rameshwor", getActualName);
    }
    @Test
    void shouldThrowWhenFullNameIsLessThanTwoCharacter(){
        assertThrows(IllegalArgumentException.class, ()->{
            new User(
                    "a",
                    "Ramchor_1233$",
                    Role.STUDENT
            );
        });
    }
    @Test
    void shouldReturnPassword(){
        assertEquals("Bikashbikash_123$", validUser.getPassword());
    }
    @Test
    void shouldTrimSpaces(){
        User user = new User(
                "atmaram tukaram bhide",
                "    YYikashbikash_123$   ",
                Role.TEACHER
        );
        String getPassword = user.getPassword();
        assertEquals("YYikashbikash_123$", user.getPassword());
    }
    @Test
    void shouldThrowWhenPasswordIsNull(){
        assertThrows(IllegalArgumentException.class, ()->{
           new User(
   "aatmaram tukaram",
  null,
            Role.TEACHER
           );
        });
    }
    @Test
    void shouldThrowWhenPasswordIsLessThanEightCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(
                    "aatmaram tukaram",
                    "Hira_2$",
                    Role.TEACHER
            );
        });
    }
}
