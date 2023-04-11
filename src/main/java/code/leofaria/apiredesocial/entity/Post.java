package code.leofaria.apiredesocial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private final LocalDateTime timestampPostCreated = LocalDateTime.now();
    
    @Column(name = "timestamp_post_updated")
    private LocalDateTime timestampPostUpdated;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profileID", foreignKey = @ForeignKey(name = "fk_post_owner"))
    private Profile profileID;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "postid", foreignKey = @ForeignKey(name = "fk_postid")),
            inverseJoinColumns = @JoinColumn(name = "profileid", foreignKey = @ForeignKey(name = "fk_profileid")))
    private List<Profile> profilePostLike;

}