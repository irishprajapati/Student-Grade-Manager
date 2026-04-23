package model;

public enum Gender {
    MALE, FEMALE, OTHER;
    public static Gender fromString(String value){
        if(value == null || value.isEmpty()){
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        for(Gender g: Gender.values()){
            if(g.name().equalsIgnoreCase(value.trim())){
                return g;
            }
        }
        throw new IllegalArgumentException("Invalid gender '" + value + "'. Accepted values: MALE, FEMALE, OTHER");
    }
}
