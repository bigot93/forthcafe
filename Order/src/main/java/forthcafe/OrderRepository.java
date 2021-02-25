package forthcafe;

import org.springframework.data.repository.PagingAndSortingRepository;

// command = repository, service 객체
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{

}