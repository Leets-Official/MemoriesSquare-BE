package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CountPhotoByDateImpl implements CountPhotoByDate {
    private final PhotoRepository photoRepository;

    @Override
    public Map<String, Long> execute(UUID userId, int year, int month) {
        List<Map<String, Object>> count = photoRepository.countByYearAndMonth(userId, year, month);
        Map<String, Long> result = new HashMap<>();
        count.forEach(i -> result.put(i.get("date").toString(), (Long) i.get("count")));
        return result;
    }
}