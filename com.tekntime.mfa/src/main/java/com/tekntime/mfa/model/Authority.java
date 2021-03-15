package com.tekntime.mfa.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter 
@Setter 
@NoArgsConstructor
@Entity(name = "authorities")
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   // @ManyToMany(mappedBy = "roles")
   // private Collection<UserLogin> users;
    private int userid;
    
    private String username;
    
    @Transient
    private Collection<Privilege> privileges;

    private String authority;



    public Authority(final String name) {
        super();
        this.authority = name;
    }

   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authority == null) ? 0 : authority.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Authority role = (Authority) obj;
        if (!authority.equals(role.authority)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Role [name=").append(authority).append("]").append("[id=").append(id).append("]");
        return builder.toString();
    }
}