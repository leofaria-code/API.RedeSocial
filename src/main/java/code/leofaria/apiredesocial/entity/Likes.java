//package code.leofaria.apiredesocial.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name="likes")
//public class Likes {
//
//    @EmbeddedId
//    private LikesId id;
//
//    @MapsId("post")
//    @ManyToOne
//    @JoinColumn(name = "postid", insertable=false, updatable=false)
//    private Post post;
//
//    @MapsId("profile")
//    @ManyToOne
//    @JoinColumn(name = "profileid", insertable=false, updatable=false)
//    private Profile profile;
//
//}
