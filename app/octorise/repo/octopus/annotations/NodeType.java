package octorise.repo.octopus.annotations;

import octorise.repo.octopus.repositories.NodesRepo;
import octorise.repo.octopus.services.NodeService;

/**
 *
 */
public @interface NodeType {

    String name();

    Class<? extends NodeService> service();
    Class<? extends NodesRepo> repo();
}
