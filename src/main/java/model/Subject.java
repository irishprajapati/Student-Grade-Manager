package model;

import java.time.LocalDateTime;

public class Subject {
    private int id;
    private String name;
    private int teacherId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Subject(int id, String name, int teacherId, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.name = validateName(name);
        this.teacherId = validateTeacherId(teacherId);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Subject(String name, int teacherId){
        this.name = validateName(name);
        this.teacherId = validateTeacherId(teacherId);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTeacherId() {
        return teacherId;
    }
    private String validateName(String name){
        if(name == null) throw new IllegalArgumentException("Name cannot be null");
        name = name.trim();
        if(name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        name = name.replaceAll("\\s+", " ");
        if(name.length()<3 || name.length() > 50) throw new IllegalArgumentException("Name length invalid");
        if (!name.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("Name contains invalid characters");
        }
        return name;
    }
    private int validateTeacherId(int teacherId){
        if(teacherId <=0) throw new IllegalArgumentException("TeacherId invalid");
        return teacherId;
    }

    public void setName(String name) {
        this.name = validateName(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void setTeacherId(int teacherId) {
        this.teacherId =validateTeacherId(teacherId);
        this.updatedAt = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherId=" + teacherId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
