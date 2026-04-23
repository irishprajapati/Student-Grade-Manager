package model;

public enum Role {
    STUDENT, TEACHER, ADMIN;
    public static Role fromString(String value){
        if(value == null || value.isEmpty()){
            throw new IllegalArgumentException("Role cannot be empty");
        }
        for(Role r: Role.values()){
            if(r.name().equalsIgnoreCase(value.trim())){
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid role " + value  + " Accepted values are: STUDENT, TEACHER, ADMIN");
    }
}
