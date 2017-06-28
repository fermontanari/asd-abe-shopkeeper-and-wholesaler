package shopkeeper.proposal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "proposal", path = "proposal")
public interface ProposalRepository extends CrudRepository<Proposal, Long>{

}
