package code.leofaria.apiredesocial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="profiles", uniqueConstraints = {@UniqueConstraint(name = "username", columnNames = "username")})
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileID;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, updatable = false)
    private final LocalDateTime timestampProfileCreated = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDate dob;
    
    @OneToMany(mappedBy = "profileOwner")
    private List<Post> ownedPosts;
    
    @ManyToMany(mappedBy = "profilePostLike")
    private List<Post> likePosts;
    
//    @OneToMany(mappedBy = "profileID")
//    private List<Profile> followers;
//
//    @OneToMany(mappedBy = "profileID")
//    private List<Profile> following;
}
