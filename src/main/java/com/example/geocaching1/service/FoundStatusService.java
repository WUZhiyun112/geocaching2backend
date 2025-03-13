package com.example.geocaching1.service;

import com.example.geocaching1.entity.FoundStatus;
import com.example.geocaching1.entity.User;
import com.example.geocaching1.repository.FoundStatusRepository;
import com.example.geocaching1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FoundStatusService {

    @Autowired
    private FoundStatusRepository foundStatusRepository;

    @Autowired
    private UserRepository userRepository;

    public FoundStatus setFoundStatus(Integer userId, String geocacheCode, String geocacheName, String geocacheType, String location, String myStatus) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }

        FoundStatus foundStatus = new FoundStatus();
        foundStatus.setUser(user.get());
        foundStatus.setGeocacheCode(geocacheCode);
        foundStatus.setGeocacheName(geocacheName);
        foundStatus.setGeocacheType(geocacheType);
        foundStatus.setLocation(location);

        // Set the status
        foundStatus.setStatus(myStatus);

        // Calculate foundAt based on the status
        foundStatus.setFoundAt(myStatus.equals("Found") ? LocalDateTime.now() : null);

        return foundStatusRepository.save(foundStatus);
    }

    public List<FoundStatus> getUserFoundGeocaches(Integer userId) {
        return foundStatusRepository.findByUserId(userId);
    }
}
