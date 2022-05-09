package esi.project.ils.extension_requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtensionRequestService {
    @Autowired
    private ExtensionRepository extensionRepository;

    public ExtensionRequest addExtensionRequest(ExtensionRequest extensionRequest) {
        return extensionRepository.save(extensionRequest);
    }

    public Optional<ExtensionRequest> updateExtensionRequest(int id, ExtensionRequest updatedExtensionRequest) {
        return extensionRepository.findById(id).map(extensionRequest -> {
            extensionRequest.setStatus(updatedExtensionRequest.getStatus());
            return extensionRequest;
        });
    }

    public Optional<ExtensionRequest> getExtensionRequest(int id) {
        return extensionRepository.findById(id);
    }

    public void deleteExtensionRequest(int id) {
        extensionRepository.deleteById(id);
    }

    public List<ExtensionRequest> getAllExtensionRequests() {
        return extensionRepository.findAll();
    }

    public List<ExtensionRequest> getExtensionRequestsWithUserId(int userId) {
        return extensionRepository.findByUserId(userId);
    }

    public List<ExtensionRequest> getExtensionRequestsWithMaterialId(int materialId) {
        return extensionRepository.findByMaterialId(materialId);
    }

    public List<ExtensionRequest> getExtensionRequestsWithStatus(String status) {
        return extensionRepository.findByStatus(status);
    }

}
