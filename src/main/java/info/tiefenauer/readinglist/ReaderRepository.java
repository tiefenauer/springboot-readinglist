package info.tiefenauer.readinglist;

import info.tiefenauer.readinglist.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, String>{
}
