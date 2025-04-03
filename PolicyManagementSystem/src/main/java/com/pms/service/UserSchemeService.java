package com.pms.service;

import com.pms.exception.InvalidEntityException;
import com.pms.entity.Scheme;
import com.pms.entity.UserSchemes;
import com.pms.repository.SchemeRepository;
import com.pms.repository.UserSchemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserSchemeService {

    @Autowired
    SchemeRepository schemeRepository;

    @Autowired
    UserSchemesRepository userSchemesRepository;

    public UserSchemes applyToScheme(int schemeId, int userId) throws InvalidEntityException {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new InvalidEntityException("Scheme not found with id: " + schemeId));

        if (!scheme.isSchemeIsActive()) {
            throw new InvalidEntityException("Scheme not active for application");
        }

        UserSchemes us = new UserSchemes(scheme, userId);
        UserSchemes savedUs = userSchemesRepository.save(us);

        return savedUs;
    }

    public List<UserSchemes> allAppliedSchemesOfUser(int userId) {
        return userSchemesRepository.findByUserId(userId);
    }

    public List<Scheme> allActiveSchemes() {
        return userSchemesRepository.findActiveSchemes();
    }

    public List<UserSchemes> getUsersAndSchemes() {
        return userSchemesRepository.findAllByUserSchemes();
    }
}
