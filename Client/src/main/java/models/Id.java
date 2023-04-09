package models;

/*
 * POJO for an Id object
 */
public class Id {
    private String userId = "";
    private String name = "";
    private String github = "";

    public Id (String name, String github) {
        // assign these field???
        this.name = name;
        this.github = github;
    }

    public String getUid() {
        return userId;
    }

    public void setUid(String uid) {
        this.userId = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.github + ") ";
    }
}