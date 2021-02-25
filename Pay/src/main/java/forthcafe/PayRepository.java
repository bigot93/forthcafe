package forthcafe;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

// command = repository, service 객체
public interface PayRepository extends PagingAndSortingRepository<Pay, Long>{

    List<Pay> findByMenuId(Long menuId);

}