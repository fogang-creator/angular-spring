package io.izmir.server.models;

import io.izmir.server.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "server")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Adresse IP unique dans la colonne et ne peut pas être vide
    @Column(unique = true)
    @NotEmpty(message = "Ip adress cannot be empty or null")
    private String ipAddress;

    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;
}

