package io.izmir.server.services;

import io.izmir.server.enumeration.Status;
import io.izmir.server.models.Server;
import io.izmir.server.repositories.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor // Lombock va faire l'injection pour nous
@Service
@Transactional
@Slf4j // print dans la console
public class ServerServiceImpl implements ServerService{

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }



    @Override
    public Server ping(String ipAdress) throws IOException {
        log.info("Pinging server : {}", ipAdress);
        Server server = serverRepository.findByIpAddress(ipAdress);
        // Ping the server
        InetAddress address = InetAddress.getByName(ipAdress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by Id : {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating the server : {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deletingting the server with the id : {}", id);
        serverRepository.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imagesNames = {"server1.png", "server2.png", "server3.png"};
        // ça va créer localhost:8080/server/image/randomNameInTheArray
        log.info(ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imagesNames[new Random().nextInt(3)]).toUriString());
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imagesNames[new Random().nextInt(3)]).toUriString();
    }
}
