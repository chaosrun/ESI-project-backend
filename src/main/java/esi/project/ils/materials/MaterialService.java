package esi.project.ils.materials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public Material addMaterial(Material material) {
        return materialRepository.save(material);
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        materialRepository.findAll().forEach(materials::add);
        return materials;
    }

    public Optional<Material> getMaterialWithId(int id) {
        return materialRepository.findById(id);
    }

    public List<Material> getMaterialWithTitle(String title) {
        return materialRepository.findByTitleContaining(title);
    }

    public List<Material> getMaterialWithType(String type) {
        return materialRepository.findByTypeContaining(type);
    }

    public List<Material> getMaterialWithAuthor(String author) {
        return materialRepository.findByAuthorContaining(author);
    }

    public Optional<Material> getMaterialWithCallNumber(String call_number) {
        return materialRepository.findByCallNumber(call_number);
    }

    public Optional<Material> updateMaterial(int id, Material updatedMaterialInformation) {
        return materialRepository.findById(id).map(material -> {
            material.setAuthor(updatedMaterialInformation.getAuthor());
            material.setTitle(updatedMaterialInformation.getTitle());
            material.setCallNumber(updatedMaterialInformation.getCallNumber());
            material.setPublishedAt(updatedMaterialInformation.getPublishedAt());
            return materialRepository.save(material);
        });
    }

    public void deleteMaterialWithId(int id) {
        materialRepository.deleteById(id);
    }

}
