package code.leofaria.apiredesocial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestampPostCreated = LocalDateTime.now();
    
    @Column(name = "timestamp_post_updated")
    private LocalDateTime timestampPostUpdated;
    
    @ManyToOne
    @JoinColumn(name = "profileID", foreignKey = @ForeignKey(name = "fk_post_owner"))
    private Profile profileOwner;
    
    @ManyToMany
    @JoinTable(name = "post_profile_likes",
            joinColumns = @JoinColumn(name = "postID", foreignKey = @ForeignKey(name = "fk_post_id")),
            inverseJoinColumns = @JoinColumn(name = "profileID", foreignKey = @ForeignKey(name = "fk_owner_id")))
    private List<Profile> profilePostLike;
    
}
