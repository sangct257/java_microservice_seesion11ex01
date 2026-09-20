package ra.demo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ra.demo.entity.Medicine;
import ra.demo.repository.MedicineRepository;
import ra.demo.service.MedicineService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;

    @Override
    @Cacheable(value = "medicines")
    public List<Medicine> getAllMedicines() {
        System.out.println("--> [DB QUERY] Đang truy vấn danh sách toàn bộ thuốc từ Database...");
        return medicineRepository.findAll();
    }

    @Override
    @Cacheable(value = "medicines", key = "#id")
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại medicine với id: " + id));
    }

    @Override
    @Cacheable(value = "medicines", key = "#medicines.medicinesId")
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    @Cacheable(value = "medicines", key = "#id")
    public Medicine updateMedicine(Long id, Medicine medicine) {
        medicineRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại medicine với id: " + id));
        medicine.setId(id);
        return medicineRepository.save(medicine);
    }

    @Override
    @Cacheable(value = "medicines", key = "#id")
    public void deleteMedicine(Long id) {
        medicineRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại medicine với id: " + id));
        medicineRepository.deleteById(id);
    }
}
