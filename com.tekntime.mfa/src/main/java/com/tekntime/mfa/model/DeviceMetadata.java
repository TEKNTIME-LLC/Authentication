package com.tekntime.mfa.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter 
@Setter 
@NoArgsConstructor
@Entity(name = "deviceMetadata")
@Table(name = "deviceMetadata")
public class DeviceMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMetadata that = (DeviceMetadata) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(deviceDetails, that.deviceDetails) &&
                Objects.equals(location, that.location) &&
                Objects.equals(lastLoggedIn, that.lastLoggedIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, deviceDetails, location, lastLoggedIn);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceMetadata{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", deviceDetails='").append(deviceDetails).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", lastLoggedIn=").append(lastLoggedIn);
        sb.append('}');
        return sb.toString();
    }
}
