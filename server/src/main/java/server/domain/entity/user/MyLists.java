package server.domain.entity.user;

import javax.persistence.*;

@Entity
@Table(name = "mylist")
public class MyLists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    String user_id;

    String name;

}
