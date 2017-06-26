package wholesaler.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "order", path = "orchestration/order")
public interface ProductsOrderRepository extends CrudRepository<ProductsOrder, Long>{

}
