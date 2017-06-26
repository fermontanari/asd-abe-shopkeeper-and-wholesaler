package shopkeeper.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "product", path = "orchestration/product")
public interface ProductRepository extends CrudRepository<Product, Long>{

}
