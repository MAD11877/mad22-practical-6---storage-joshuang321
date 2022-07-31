package sg.edu.np.mad.mad_practical2;

import java.io.Serializable;

public class User implements Serializable {
    public String name,
        description;
    public int id;
    public boolean followed;
    public User() {}

    public User(String name,
                String description,
                int id,
                boolean followed)
    {
        this.name = name;
        this.description = description;
        this.id = id;
        this.followed = followed;
    }
}
