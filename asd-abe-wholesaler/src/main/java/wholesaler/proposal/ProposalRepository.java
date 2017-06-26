package wholesaler.proposal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "proposal", path = "orchestration/proposal")
public interface ProposalRepository extends CrudRepository<Proposal, Long>{

}
