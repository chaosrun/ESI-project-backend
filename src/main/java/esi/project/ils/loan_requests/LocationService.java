package esi.project.ils.loan_requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }
}
